package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.scacchipa.twittercloneapp.databinding.ActivityLoginLayoutBinding

class LoginCloneActivity : AppCompatActivity() {

    private var binding: ActivityLoginLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
