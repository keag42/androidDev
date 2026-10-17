package ca.georgebrown.keags.lab4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CustomerApp(viewModel: CustomerViewModel, modifier: Modifier = Modifier) {
    val customers by viewModel.customers.collectAsStateWithLifecycle(initialValue = emptyList())
    val message by viewModel.message.collectAsStateWithLifecycle()
    val activeSearch by viewModel.activeSearch.collectAsStateWithLifecycle()
    CustomerScreen(customers, viewModel::addCustomer, viewModel::deleteCustomer,
        viewModel::searchCustomers, viewModel::clearSearch, activeSearch, message, modifier)
}

@Composable
fun CustomerScreen(
    customers: List<Customer>,
    onAddCustomer: (String, Int, Boolean) -> Unit,
    onDeleteCustomer: (Customer) -> Unit,
    onSearch: (String) -> Unit,
    onClearSearch: () -> Unit,
    activeSearch: String,
    databaseMessage: String?,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    // Form and records share one scrolling area so small screens and the keyboard remain usable.
    LazyColumn(
        modifier = modifier.fillMaxSize().imePadding().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Room Customer Demo", style = MaterialTheme.typography.headlineSmall)
        }
        item {
            OutlinedTextField(name, { name = it; error = null },
                label = { Text("Customer name") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
        }
        item {
            OutlinedTextField(ageText, { value ->
                if (value.all { it.isDigit() }) { ageText = value; error = null }
            }, label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true, modifier = Modifier.fillMaxWidth())
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Active customer")
                Spacer(Modifier.width(8.dp))
                Switch(isActive, { isActive = it })
            }
        }
        item {
            Button(onClick = {
                val age = ageText.toIntOrNull()
                if (name.isBlank() || age == null || age < 0) {
                    error = "Enter a valid name and age."
                } else {
                    onAddCustomer(name.trim(), age, isActive)
                    name = ""; ageText = ""; isActive = false; error = null
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Add Customer") }
        }
        item {
            val message = error ?: databaseMessage
            if (message != null) Text(message,
                color = if (error != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface)
        }
        item { HorizontalDivider() }
        item {
            OutlinedTextField(searchText, { searchText = it },
                label = { Text("Search by customer name") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onSearch(searchText) }) { Text("Search") }
                OutlinedButton(onClick = { searchText = ""; onClearSearch() }) { Text("Clear") }
            }
        }
        item {
            Text(if (activeSearch.isBlank()) "All customers" else "Results for: $activeSearch",
                style = MaterialTheme.typography.titleMedium)
        }
        customerList(customers, onDeleteCustomer, activeSearch.isNotBlank())
    }
}

// Reuse the same customer list for all records and search results.
private fun androidx.compose.foundation.lazy.LazyListScope.customerList(
    customers: List<Customer>,
    onDeleteCustomer: (Customer) -> Unit,
    searching: Boolean
) {
    if (customers.isEmpty()) {
        item {
            Text(if (searching) "No matching customers." else "No customers in the database.",
                modifier = Modifier.padding(vertical = 24.dp))
        }
    } else {
        items(customers, key = { it.id }) { customer ->
            CustomerRow(customer, onDelete = { onDeleteCustomer(customer) })
        }
    }
}

@Composable
fun CustomerRow(customer: Customer, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(customer.name, style = MaterialTheme.typography.titleMedium)
                Text("ID: ${customer.id}")
                Text("Age: ${customer.age}")
                Text(if (customer.isActive) "Active customer" else "Inactive customer")
            }
            TextButton(onClick = onDelete) { Text("Delete") }
        }
    }
}
