package `in`.miscos.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import `in`.miscos.firebasedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFirebaseDbRef: DatabaseReference
    val userList = mutableListOf<UserItem>()
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        mFirebaseDbRef = FirebaseDatabase.getInstance().getReference("/Members")
        observeUSerList()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(userList)
        binding.rvUserList.adapter = userAdapter
    }

    private fun observeUSerList() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    binding.progressCircular.visibility= View.GONE
                    for (snapshot in dataSnapshot.children) {
                        val item = snapshot.getValue(UserItem::class.java)
                        userList.add(UserItem(item?.Age, item?.City, item?.name))
                    }
                    userAdapter.sortUserList(userList)
                } catch (exp: Exception) {
                    binding.progressCircular.visibility= View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Fail due to ${exp.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                binding.progressCircular.visibility= View.GONE
                // Handle any errors here
                Log.w("FirebaseDatabase", "Failed to read value.", databaseError.toException())
            }
        }

        mFirebaseDbRef.addValueEventListener(valueEventListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_by_age -> {
                userList.sortBy { it.Age }
                userAdapter.sortUserList(userList)
                true
            }

            R.id.menu_sort_by_name -> {
                userList.sortBy { it.name }
                userAdapter.sortUserList(userList)
                true
            }

            R.id.menu_sort_by_city -> {
                userList.sortBy { it.City }
                userAdapter.sortUserList(userList)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}