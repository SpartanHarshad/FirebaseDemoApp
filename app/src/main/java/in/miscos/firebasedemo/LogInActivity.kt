package `in`.miscos.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import `in`.miscos.firebasedemo.databinding.ActivityLogInBinding
import java.util.regex.Pattern

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        perFormLogIn()
    }

    private fun perFormLogIn() {
        binding.btnLogin.setOnClickListener {
            val userName = binding.etvUserName.text.toString()
            val password = binding.etvPassword.text.toString()
            if (validateUserName(userName) && validatePassword(password)) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    /**
     * Explanation of the regex pattern:
     * ^: Start of the string.
     * (?=.*[A-Z]): Positive lookahead for at least one uppercase alphabet.
     * (?=.*[0-9]): Positive lookahead for at least one numeric digit.
     * (?=.*[!@#\$%^&*()-+_={}|;':",.<>?]): Positive lookahead for at least one special character.
     * .{7,}: Match any character (except a newline) at least 7 times.
     * $: End of the string.
     * */
    private fun validatePassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()-+_={}|;':\",.<>?]).{7,}\$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return if (matcher.matches()) {
            true
        } else {
            binding.etvPassword.error =
                "Password must be 7 Characters. \n1 UpperCase Alphabet \n1 SpecialCharacter \n1 Numeric"
            false
        }
    }

    private fun validateUserName(userName: String): Boolean {
        return if (userName.length >= 10) {
            true
        } else {
            binding.etvUserName.error = "Username must be 10 characters"
            false
        }
    }
}