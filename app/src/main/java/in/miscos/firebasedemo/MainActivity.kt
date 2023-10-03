package `in`.miscos.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import `in`.miscos.firebasedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFirebaseDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        mFirebaseDbRef = FirebaseDatabase.getInstance().getReference("/Members")
        observeUSerList()
    }

    private fun observeUSerList() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Initialize a list to hold the items
                val userList = mutableListOf<UserItem>()

                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(UserItem::class.java)
                    item?.let {
                        userList.add(it)
                    }
                }

                // Do something with the retrieved itemList
                // For example, update the UI or log the items
                userList.forEach {
                    Log.d("Item", "Name: ${it.name}, age: ${it.age}")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here
                Log.w("FirebaseDatabase", "Failed to read value.", databaseError.toException())
            }
        }

         // Attach the listener to the database reference
        mFirebaseDbRef.addValueEventListener(valueEventListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_by_age -> {
                //Selected Age
                true
            }

            R.id.menu_sort_by_name -> {
                //Selected Name
                true
            }

            R.id.menu_sort_by_city -> {
                //Selected City
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}