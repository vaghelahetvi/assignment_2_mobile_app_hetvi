package com.example.locationfinder

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database version and name
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LocationFinder.db"

        // Table and column names
        const val TABLE_LOCATIONS = "locations"
        const val COLUMN_ID = "id"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // SQL command to create the locations table
        val createTable = ("CREATE TABLE $TABLE_LOCATIONS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_ADDRESS TEXT, "
                + "$COLUMN_LATITUDE REAL, "
                + "$COLUMN_LONGITUDE REAL)")
        db.execSQL(createTable)

        // Insert initial data into the table
        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Predefined locations with coordinates
        val locations = listOf(
            "Oshawa" to Pair(43.8971, -78.8658),
            "Toronto" to Pair(43.65107, -79.347015),
            "Mississauga" to Pair(43.5890, -79.6441),
            "Ajax" to Pair(43.8509, -79.0204),
            "Pickering" to Pair(43.8353, -79.0868),
            "Scarborough" to Pair(43.7731, -79.2578),
            "Brampton" to Pair(43.7315, -79.7624),
            "Markham" to Pair(43.8561, -79.3370)
        )

        // Loop to insert 100 entries by cycling through predefined locations
        for (i in 1..100) {
            val (address, coordinates) = locations[i % locations.size] // Pick location in loop
            val latitude = coordinates.first
            val longitude = coordinates.second

            // Insert location entry into the table
            db.execSQL("INSERT INTO $TABLE_LOCATIONS ($COLUMN_ADDRESS, $COLUMN_LATITUDE, $COLUMN_LONGITUDE) VALUES ('$address', $latitude, $longitude)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drops old table if it exists, and recreates it
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        onCreate(db)
    }

    // CREATE (Add new location to the database)
    fun addLocation(address: String, latitude: Double, longitude: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ADDRESS, address) // Store address
            put(COLUMN_LATITUDE, latitude) // Store latitude
            put(COLUMN_LONGITUDE, longitude) // Store longitude
        }
        val result = db.insert(TABLE_LOCATIONS, null, values) // Insert row
        db.close()
        return result // Return the row ID
    }

    // READ (Retrieve location by address)
    fun getLocationByAddress(address: String): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_LOCATIONS, // Table name
            arrayOf(COLUMN_LATITUDE, COLUMN_LONGITUDE), // Columns to retrieve
            "$COLUMN_ADDRESS = ?", // WHERE clause for address
            arrayOf(address), // Argument for WHERE clause
            null, // Group By (not used)
            null, // Having (not used)
            null  // Order By (not used)
        )
    }

    // UPDATE (Update location details by ID)
    fun updateLocation(id: Int, address: String, latitude: Double, longitude: Double): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ADDRESS, address) // Update address
            put(COLUMN_LATITUDE, latitude) // Update latitude
            put(COLUMN_LONGITUDE, longitude) // Update longitude
        }
        val result = db.update(TABLE_LOCATIONS, values, "$COLUMN_ID = ?", arrayOf(id.toString())) // Update row
        db.close()
        return result // Return the number of rows updated
    }

    // DELETE (Delete a location by ID)
    fun deleteLocation(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_LOCATIONS, "$COLUMN_ID = ?", arrayOf(id.toString())) // Delete row by ID
        db.close()
        return result // Return the number of rows deleted
    }
}