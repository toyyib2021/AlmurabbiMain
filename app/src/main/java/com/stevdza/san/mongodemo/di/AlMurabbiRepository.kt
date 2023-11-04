package com.stevdza.san.mongodemo.di

import com.stevdza.san.mongodemo.data.auth.Auth
import com.stevdza.san.mongodemo.data.book.BookProduced
import com.stevdza.san.mongodemo.data.customer.Customer
import com.stevdza.san.mongodemo.data.order.Order
import com.stevdza.san.mongodemo.data.priceList.Price
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface AlMurabbiRepository {

    fun configureTheRealm()

    // Orders
    fun getOrder(): Flow<List<Order>>
    fun filterOrderWithSchoolName(schoolName: String): Flow<List<Order>>
    suspend fun getOrderWithID(_id: ObjectId): Order
    suspend fun insertOrder(order: Order)
    suspend fun updateOrder(order: Order)


    // Books
    fun getBookProduced(): Flow<List<BookProduced>>
    fun filterData(bookName: String): Flow<List<BookProduced>>
    suspend fun insertBookRecord(bookProduced: BookProduced)
    suspend fun updateBookRecord(bookProduced: BookProduced)
    suspend fun deleteBookRecord(id: ObjectId)


    // Customer
    fun getCustomer(): Flow<List<Customer>>
    fun filterWithSchoolName(schoolName: String): Flow<List<Customer>>
    fun getCustomer(schoolName: String): Customer?
    //    fun filterWithSchoolPhoneNumber(schoolPhoneNumber: String): Flow<List<Customer>>
    suspend fun insertCustomer(customer: Customer)
    suspend fun updateCustomer(customer: Customer)
    suspend fun deleteCustomer(id: ObjectId)


    // Auth
    fun getData(): Flow<List<Auth>>
    suspend fun getAuthWithID(_id: ObjectId): Auth
    suspend fun insertAuth(auth: Auth)
    suspend fun updateAuth(auth: Auth)
    suspend fun deleteAuth(id: ObjectId)


    // Price
    fun getPriceList(): Flow<List<Price>>
    fun filterWithBookName(bookName: String): Flow<List<Price>>
    suspend fun insertPrice(price: Price)
    suspend fun updatePrice(price: Price)
    suspend fun deletePrice(id: ObjectId)






}


