package ca.georgebrown.keags.lab4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CustomerViewModel(private val customerDao: CustomerDao) : ViewModel() {
    private val searchText = MutableStateFlow("")
    val activeSearch = searchText.asStateFlow()
    val customers: Flow<List<Customer>> = searchText.flatMapLatest { query ->
        if (query.isBlank()) customerDao.getAllCustomers()
        else customerDao.searchCustomers(query)
    }
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun searchCustomers(text: String) { searchText.value = text.trim() }
    fun clearSearch() { searchText.value = "" }

    fun addCustomer(name: String, age: Int, isActive: Boolean) {
        if (name.isBlank() || age < 0) return
        viewModelScope.launch {
            try {
                customerDao.insert(Customer(name = name.trim(), age = age, isActive = isActive))
                _message.value = "Customer saved."
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _message.value = "Could not save customer."
            }
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                customerDao.delete(customer)
                _message.value = "Customer deleted."
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _message.value = "Could not delete customer."
            }
        }
    }
}

class CustomerViewModelFactory(private val customerDao: CustomerDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            return CustomerViewModel(customerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
