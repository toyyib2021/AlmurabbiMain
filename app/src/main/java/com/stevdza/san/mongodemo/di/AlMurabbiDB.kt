package com.stevdza.san.mongodemo.di

import android.util.Log
import com.stevdza.san.mongodemo.data.auth.Auth
import com.stevdza.san.mongodemo.data.book.BookProduced
import com.stevdza.san.mongodemo.data.customer.Customer
import com.stevdza.san.mongodemo.data.order.Order
import com.stevdza.san.mongodemo.data.order.OrderDetails
import com.stevdza.san.mongodemo.data.order.Payment
import com.stevdza.san.mongodemo.data.priceList.Price
import com.stevdza.san.mongodemo.ui.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

object AlMurabbiDB : AlMurabbiRepository {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user,
                setOf(
                    Order::class, OrderDetails::class, Payment::class,
                    BookProduced::class, Customer::class, Price::class,
                    Auth::class))
                .initialSubscriptions { sub ->
                    add(query = sub.query<Order>(query = "owner_id == $0", "user_id"))
                    add(query = sub.query<BookProduced>(query = "owner_id == $0", "user_id"))
                    add(query = sub.query<Customer>(query = "owner_id == $0", "user_id"))
                    add(query = sub.query<Price>(query = "owner_id == $0", "user_id"))
                    add(query = sub.query<Auth>(query = "owner_id == $0", "user_id"))
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    // Order
    override fun getOrder(): Flow<List<Order>> {
        return realm.query<Order>().asFlow().map { it.list }
    }

    override fun filterOrderWithSchoolName(schoolName: String): Flow<List<Order>> {
        return realm.query<Order>(query = "schoolName CONTAINS[c] $0", schoolName).asFlow().map { it.list }
    }

    override suspend fun getOrderWithID(_id: ObjectId): Order {
        return realm.query<Order>(query = "_id == $0", _id).first().find() as Order
    }

    override suspend fun insertOrder(order: Order) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(order.apply { owner_id = "user_id" })
                } catch (e: Exception) {
                    Log.d("MongoRepository", e.message.toString())
                }
            }
        }
    }

    override suspend fun updateOrder(order: Order) {
        realm.write {
            val queriedPerson = query<Order>(query = "_id == $0", order._id).first().find()
            queriedPerson?.schoolName= order.schoolName
            queriedPerson?.schoolPhone= order.schoolPhone
            queriedPerson?.paid= order.paid
            queriedPerson?.date= order.date
            queriedPerson?.priceType= order.priceType
            queriedPerson?.totalAmount= order.totalAmount
            queriedPerson?.orderDetails= order.orderDetails
            queriedPerson?.payment= order.payment

        }
    }


    // Books
    override fun getBookProduced(): Flow<List<BookProduced>> {
        return realm.query<BookProduced>().asFlow().map { it.list }
    }

    override fun filterData(bookName: String): Flow<List<BookProduced>> {
        return realm.query<BookProduced>(query = "bookName CONTAINS[c] $0", bookName)
            .asFlow().map { it.list }
    }

    override suspend fun insertBookRecord(bookProduced: BookProduced) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(bookProduced.apply { owner_id = "user_id" })
                } catch (e: Exception) {
                    Log.d("MongoRepository", e.message.toString())
                }
            }
        }
    }

    override suspend fun updateBookRecord(bookProduced: BookProduced) {
        realm.write {
            val queriedPerson =
                query<BookProduced>(query = "_id == $0", bookProduced._id)
                    .first()
                    .find()
            if (queriedPerson != null) {
                queriedPerson.bookName = bookProduced.bookName
                queriedPerson.quantityAdd = bookProduced.quantityAdd
                queriedPerson.date = bookProduced.date

            } else {
                Log.d("MongoRepository", "Queried Person does not exist.")
            }
        }
    }

    override suspend fun deleteBookRecord(id: ObjectId) {
        realm.write {
            try {
                val bookProduced = query<BookProduced>(query = "_id == $0", id)
                    .first()
                    .find()
                bookProduced?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("MongoRepository", "${e.message}")
            }
        }
    }

    // Customer
    override fun getCustomer(): Flow<List<Customer>> {
        return realm.query<Customer>().asFlow().map { it.list }
    }

    override fun filterWithSchoolName(schoolName: String): Flow<List<Customer>> {
        return realm.query<Customer>(query = "schoolName CONTAINS[c] $0", schoolName)
            .asFlow().map { it.list }
    }

    override fun getCustomer(schoolName: String): Customer? {
        val cus = realm.query<Customer>(query = "schoolName CONTAINS[c] $0", schoolName).first().find()
        return cus
    }

    override suspend fun insertCustomer(customer: Customer) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(customer.apply { owner_id = "user_id" })
                } catch (e: Exception) {
                    Log.d("MongoRepository", e.message.toString())
                }
            }
        }
    }

    override suspend fun updateCustomer(customer: Customer) {
        realm.write {
            val queriedCustomer =
                query<Customer>(query = "_id == $0", customer._id)
                    .first()
                    .find()
            if (queriedCustomer != null) {
                queriedCustomer.schoolName = customer.schoolName
                queriedCustomer.schoolRepName = customer.schoolRepName
                queriedCustomer.address = customer.address
                queriedCustomer.schoolPhoneNumber = customer.schoolPhoneNumber
                queriedCustomer.repPhoneNumber = customer.repPhoneNumber
                queriedCustomer.image = customer.image
                queriedCustomer.hide = customer.hide
                queriedCustomer.customerType = customer.customerType

            } else {
                Log.d("MongoRepository", "Queried Person does not exist.")
            }
        }
    }

    override suspend fun deleteCustomer(id: ObjectId) {
        realm.write {
            try {
                val customer = query<Customer>(query = "_id == $0", id)
                    .first()
                    .find()
                customer?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("MongoRepository", "${e.message}")
            }
        }
    }


    //  Price
    override fun getPriceList(): Flow<List<Price>> {
        return realm.query<Price>().asFlow().map { it.list }
    }

    override fun filterWithBookName(bookName: String): Flow<List<Price>> {
        return realm.query<Price>(query = "schoolName CONTAINS[c] $0", bookName)
            .asFlow().map { it.list }
    }

    override suspend fun insertPrice(price: Price) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(price.apply { owner_id = "user_id" })
                } catch (e: Exception) {
                    Log.d("MongoRepository", e.message.toString())
                }
            }
        }
    }

    override suspend fun updatePrice(price: Price) {
        realm.write {
            val queriedPrice =
                query<Price>(query = "_id == $0", price._id)
                    .first()
                    .find()
            if (queriedPrice != null) {
                queriedPrice.bookName = price.bookName
                queriedPrice.price = price.price
                queriedPrice.discountPrice = price.discountPrice
                queriedPrice.repPrice = price.repPrice
                queriedPrice.repPriceDiscount = price.repPriceDiscount
                queriedPrice.priceDiscountAlt = price.priceDiscountAlt
            } else {
                Log.d("MongoRepository", "Queried Person does not exist.")
            }
        }
    }

    override suspend fun deletePrice(id: ObjectId) {
        realm.write {
            try {
                val price = query<Price>(query = "_id == $0", id)
                    .first()
                    .find()
                price?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("MongoRepository", "${e.message}")
            }
        }
    }

    override fun getData(): Flow<List<Auth>> {
        return realm.query<Auth>().asFlow().map { it.list }
    }


    // Access Key and Sales key
    override suspend fun getAuthWithID(_id: ObjectId): Auth {
        return realm.query<Auth>(query = "_id == $0", _id).first().find() as Auth
    }

    override suspend fun insertAuth(auth: Auth) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(auth.apply { owner_id = "user_id" })
                } catch (e: Exception) {
                    Log.d("MongoRepository", e.message.toString())
                }
            }
        }
    }

    override suspend fun updateAuth(auth: Auth) {
        realm.write {
            val queriedPerson =
                query<Auth>(query = "_id == $0", auth._id)
                    .first()
                    .find()
            if (queriedPerson != null) {
                queriedPerson.accessKey = auth.accessKey
                queriedPerson.sellerKey = auth.sellerKey

            } else {
                Log.d("MongoRepository", "Queried Person does not exist.")
            }
        }
    }

    override suspend fun deleteAuth(id: ObjectId) {
        realm.write {
            try {
                val auth = query<Auth>(query = "_id == $0", id)
                    .first()
                    .find()
                auth?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("MongoRepository", "${e.message}")
            }
        }
    }



}