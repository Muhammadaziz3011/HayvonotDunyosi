package DB

import Models.Hayvon
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context?)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), DbService{

    companion object{
        const val DB_NAME = "hayvon_db"
        const val DB_VERSION = 1

        const val TABLE_NAME_YER = "table_ogohlantiruvchi"
        const val ID_OGOH = "id"
        const val NAME_OGOH = "name"
        const val MATN_YER = "matn"
        const val IMAGE_YER = "image_path"
        const val LIKE_YER = "like"
        const val CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryOgogh = "create table $TABLE_NAME_YER ($ID_OGOH integer not null primary key autoincrement unique, $NAME_OGOH text not null, $MATN_YER text not null, $IMAGE_YER text not null, $LIKE_YER integer not null, $CATEGORY integer not null)"

        db?.execSQL(queryOgogh)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    override fun addLabel(hayvon: Hayvon) {
        val dataBase = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(NAME_OGOH, hayvon.name)
        contentValue.put(MATN_YER, hayvon.matni)
        contentValue.put(IMAGE_YER, hayvon.imagePath)
        contentValue.put(LIKE_YER, hayvon.like)
        contentValue.put(CATEGORY, hayvon.category)
        dataBase.insert(TABLE_NAME_YER, null, contentValue)
        dataBase.close()
    }

    override fun editLabel(hayvon: Hayvon): Int {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(NAME_OGOH, hayvon.name)
        contentValue.put(MATN_YER, hayvon.matni)
        contentValue.put(IMAGE_YER, hayvon.imagePath)
        contentValue.put(LIKE_YER, hayvon.like)
        contentValue.put(CATEGORY, hayvon.category)

        return database.update(TABLE_NAME_YER, contentValue, "$ID_OGOH = ?", arrayOf(hayvon.id.toString()))
    }

    override fun deleteLabel(hayvon: Hayvon) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME_YER, "$ID_OGOH = ?", arrayOf("${hayvon.id}"))
        database.close()
    }

    override fun getAllLabel(): ArrayList<Hayvon> {
        val list = ArrayList<Hayvon>()
        val query = "select * from $TABLE_NAME_YER"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val matni = cursor.getString(2)
                val imagePath = cursor.getString(3)
                val like = cursor.getInt(4)
                val category = cursor.getInt(5)
                val contact = Hayvon(id, name, matni, imagePath, like, category)
                list.add(contact)
            }while (cursor.moveToNext())
        }
        return list
    }
}