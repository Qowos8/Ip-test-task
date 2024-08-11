package com.example.ip_test_task.presentation

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.ip_test_task.R
import com.example.ip_test_task.di.AppComponentHolder
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.ui.theme.appBarColor
import com.example.ip_test_task.ui.theme.cardColors
import com.example.ip_test_task.ui.theme.dialogColor
import com.example.ip_test_task.ui.theme.topAppBarColor
import com.example.ip_test_task.utils.MockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val productViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponentHolder.appComponent.injectMainActivity(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = appBarColor.toArgb()
        window.navigationBarColor = appBarColor.toArgb()
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightNavigationBars = true
        insetsController.isAppearanceLightStatusBars = true

        setContent {
            productViewModel.getAll()
            collectSearchQuery(this, productViewModel)
            Render(productViewModel)
        }
    }
}

@Composable
fun Render(productViewModel: MainViewModel) {
    val state by productViewModel.productState.collectAsState(initial = ProductState.Init)

    when (state) {
        is ProductState.Init -> Unit

        is ProductState.Error -> {
            Log.d("main", (state as ProductState.Error).error)
        }

        is ProductState.Success -> {
            ProductListScreen(
                products = (state as ProductState.Success).query,
                viewModel = productViewModel
            )
        }

        ProductState.Empty -> {
            LaunchedEffect(Unit) {
                addMock(productViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(products: List<ProductItem>, viewModel: MainViewModel) {
    var productName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.product_list))
                    }
                },
                colors = topAppBarColor,
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = {
                        productName = it
                        CoroutineScope(Dispatchers.Default).launch {
                            viewModel.currentSearch.emit(productName)
                        }
                    },
                    label = { Text(stringResource(R.string.search_product)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 0.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    },

                    )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(products.size) { index ->
                        ProductCard(products[index], viewModel::delete, viewModel)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(product: ProductItem, onDelete: (ProductItem) -> Unit, viewModel: MainViewModel) {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val dateAdded = sdf.format(Date(product.time))

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditQuantityDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteProductDialog(
            onConfirm = {
                onDelete(product)
                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }

    if (showEditQuantityDialog) {
        EditProductQuantityDialog(
            quantity = product.amount,
            onQuantityChange = { newCount ->
               viewModel.updateProductAmount(product, newCount)
            },
            onConfirm = {
                showEditQuantityDialog = false
            },
            onDismiss = {
                showEditQuantityDialog = false
            }
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = cardColors,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showEditQuantityDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF650CE8)
                    )
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFC94400)
                    )
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy((-10).dp)
            ) {
                product.tags.forEach { tag ->
                    FilterChip(
                        onClick = { },
                        label = { Text(text = tag) },
                        selected = false,
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            labelColor = Color.Black
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(R.string.count_word),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    stringResource(R.string.date_word),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "${product.amount}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = dateAdded,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun DeleteProductDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        icon = {
            Icon(imageVector = Icons.Default.Warning, contentDescription = "delete icon")
        },
        title = {
            Text(text = stringResource(R.string.delete_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.delete_dialog_text))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.yes_word), color = dialogColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.no_word), color = dialogColor)
            }
        })
}

@Composable
private fun EditProductQuantityDialog(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var currentQuantity by remember { mutableIntStateOf(quantity) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        icon = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "icon")
        },
        title = {
            Text(text = stringResource(R.string.count_products))
        },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { if (currentQuantity > 0) currentQuantity-- }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_remove_circle_outline_24),
                        contentDescription = "Decrease",
                        tint = dialogColor,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(text = currentQuantity.toString(), fontSize = 20.sp)
                IconButton(onClick = { currentQuantity++ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_circle_outline_24),
                        contentDescription = "Increase",
                        tint = dialogColor,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onQuantityChange(currentQuantity)
                onConfirm()
            }) {
                Text(stringResource(R.string.confirm_text), color = dialogColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dismiss_text), color = dialogColor)
            }
        }
    )
}

private fun collectSearchQuery(context: MainActivity, viewModel: MainViewModel) {
    context.lifecycleScope.launch {
        viewModel.queryState.collect { state ->
            when (state) {
                is SearchState.Error -> Unit

                SearchState.Init -> Unit
                is SearchState.Success -> {
                    viewModel.searchProduct(state.query)
                }
            }
        }
    }
}

private fun addMock(viewModel: MainViewModel) {
    MockData.products.forEach { product ->
        viewModel.insert(product)
    }
}