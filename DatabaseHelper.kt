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
    // List of 100 cities with their coordinates
    val locations = listOf(
        "Oshawa" to Pair(43.8971, -78.8658),
        "Toronto" to Pair(43.65107, -79.347015),
        "Mississauga" to Pair(43.5890, -79.6441),
        "Ajax" to Pair(43.8509, -79.0204),
        "Pickering" to Pair(43.8353, -79.0868),
        "Scarborough" to Pair(43.7731, -79.2578),
        "Brampton" to Pair(43.7315, -79.7624),
        "Markham" to Pair(43.8561, -79.3370),
        "Hamilton" to Pair(43.2557, -79.8711),
        "Ottawa" to Pair(45.4215, -75.6972),
        "Kingston" to Pair(44.2312, -76.4860),
        "Waterloo" to Pair(43.4643, -80.5204),
        "Cambridge" to Pair(43.3616, -80.3144),
        "Niagara Falls" to Pair(43.0896, -79.0849),
        "Windsor" to Pair(42.3149, -83.0364),
        "Kitchener" to Pair(43.4516, -80.4925),
        "Guelph" to Pair(43.5448, -80.2482),
        "London" to Pair(42.9849, -81.2453),
        "Sudbury" to Pair(46.5222, -80.9530),
        "Sault Ste. Marie" to Pair(46.5219, -84.3461),
        "Thunder Bay" to Pair(48.3809, -89.2477),
        "Barrie" to Pair(44.3894, -79.6903),
        "Peterborough" to Pair(44.3091, -78.3197),
        "North Bay" to Pair(46.3091, -79.4608),
        "Timmins" to Pair(48.4758, -81.3305),
        "Belleville" to Pair(44.1628, -77.3832),
        "Orillia" to Pair(44.6082, -79.4191),
        "Brockville" to Pair(44.5895, -75.6843),
        "Cornwall" to Pair(45.0210, -74.7304),
        "Stratford" to Pair(43.3700, -80.9822),
        "St. Catharines" to Pair(43.1594, -79.2469),
        "Brantford" to Pair(43.1394, -80.2644),
        "Milton" to Pair(43.5183, -79.8774),
        "Oakville" to Pair(43.4675, -79.6877),
        "Burlington" to Pair(43.3255, -79.7990),
        "Georgetown" to Pair(43.6457, -79.9210),
        "Caledon" to Pair(43.8554, -79.8763),
        "Vaughan" to Pair(43.8361, -79.4983),
        "Richmond Hill" to Pair(43.8828, -79.4403),
        "Newmarket" to Pair(44.0592, -79.4613),
        "Aurora" to Pair(44.0065, -79.4504),
        "Orangeville" to Pair(43.9199, -80.0943),
        "Goderich" to Pair(43.7408, -81.7071),
        "Sarnia" to Pair(42.9745, -82.4066),
        "Huntsville" to Pair(45.3269, -79.2168),
        "Bracebridge" to Pair(45.0386, -79.3076),
        "Collingwood" to Pair(44.5001, -80.2169),
        "Wasaga Beach" to Pair(44.5205, -80.0164),
        "Midland" to Pair(44.7497, -79.8920),
        "Parry Sound" to Pair(45.3434, -80.0362),
        "Gravenhurst" to Pair(44.9197, -79.3754),
        "Kenora" to Pair(49.7672, -94.4895),
        "Dryden" to Pair(49.7857, -92.8375),
        "Kapuskasing" to Pair(49.4236, -82.4312),
        "Elliot Lake" to Pair(46.3832, -82.6523),
        "Hearst" to Pair(49.6865, -83.6624),
        "Fort Frances" to Pair(48.6092, -93.4050),
        "Sioux Lookout" to Pair(50.0955, -91.9215),
        "Red Lake" to Pair(51.0145, -93.8281),
        "Atikokan" to Pair(48.7587, -91.6212),
        "Wawa" to Pair(47.9949, -84.7713),
        "Marathon" to Pair(48.7277, -86.3824),
        "Geraldton" to Pair(49.7162, -86.9477),
        "Terrace Bay" to Pair(48.7836, -87.0989),
        "Nipigon" to Pair(49.0121, -88.2681),
        "Temiskaming Shores" to Pair(47.5009, -79.6829),
        "Moosonee" to Pair(51.2738, -80.6492),
        "Kirkland Lake" to Pair(48.1522, -80.0340),
        "Cochrane" to Pair(49.1733, -81.0644),
        "Englehart" to Pair(47.8230, -79.8727),
        "Little Current" to Pair(45.9781, -81.9258),
        "Blind River" to Pair(46.1850, -82.9641),
        "Spanish" to Pair(46.1878, -82.5518),
        "Gore Bay" to Pair(45.9168, -82.4724),
        "Hagersville" to Pair(42.9596, -80.0504),
        "Simcoe" to Pair(42.8357, -80.3040),
        "Port Dover" to Pair(42.7870, -80.2030),
        "Dunnville" to Pair(42.9039, -79.6165),
        "Tillsonburg" to Pair(42.8631, -80.7321),
        "Aylmer" to Pair(42.7731, -80.9834),
        "St. Thomas" to Pair(42.7741, -81.1819),
        "Leamington" to Pair(42.0592, -82.5994),
        "Kingsville" to Pair(42.0384, -82.7395),
        "Amherstburg" to Pair(42.1014, -83.0640),
        "Tecumseh" to Pair(42.2727, -82.8917),
        "Essex" to Pair(42.1766, -82.8213),
        "Harrow" to Pair(42.0342, -82.9230),
        "Lasalle" to Pair(42.2231, -83.0534),
        "Petrolia" to Pair(42.8794, -82.1512),
        "Forest" to Pair(43.0942, -82.0045),
        "Grand Bend" to Pair(43.3136, -81.7567),
        "Exeter" to Pair(43.3499, -81.4804)
    )

    // Insert each city and coordinates into the database
    for ((address, coordinates) in locations) {
        val latitude = coordinates.first
        val longitude = coordinates.second
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
