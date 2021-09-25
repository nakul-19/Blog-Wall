package com.nakul.blogWall.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.databinding.ActivityNewBlogBinding
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.utils.UtilConstants
import com.nakul.blogWall.viewmodels.NewBlogViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewBlogActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewBlogBinding
    lateinit var category: String
    private val viewmodel: NewBlogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClick()
        setObserver()

    }

    private fun setObserver() {

        val lSnackbar = Snackbar.make(binding.root,"Posting Blog...",Snackbar.LENGTH_INDEFINITE)
        val sSnackbar = Snackbar.make(binding.root,"Blog posted Successfully",Snackbar.LENGTH_SHORT)
        val eSnackbar = Snackbar.make(binding.root,"Error :/",Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
            binding.createBlog.callOnClick()
        }

        viewmodel.response.observe(this) {
            when(it) {
                is Event.Success -> {
                    lSnackbar.dismiss()
                    eSnackbar.dismiss()
                    sSnackbar.show()
                }
                is Event.Error -> {
                    sSnackbar.dismiss()
                    lSnackbar.dismiss()
                    eSnackbar.show()
                }
                is Event.Loading -> {
                    eSnackbar.dismiss()
                    sSnackbar.dismiss()
                    lSnackbar.show()
                }
            }
        }

    }

    private fun setClick() {

        binding.categorySpinner.apply {
            val list = listOf(
                UtilConstants.science,
                UtilConstants.technology,
                UtilConstants.global,
                UtilConstants.health,
                UtilConstants.entertainment,
                UtilConstants.sports
            )

            adapter =
                ArrayAdapter(
                    this@NewBlogActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    list
                )

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?, position: Int, id: Long
                ) {
                    category = list[position]
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
            setSelection(0)
        }

        binding.createBlog.setOnClickListener {
            if (viewmodel.response.value is Event.Loading)
                return@setOnClickListener
            viewmodel.createBlog(
                category,
                binding.title.text.toString(),
                binding.content.text.toString(),
                SimpleDateFormat("yyyy-MM-dd").format(Date())
            )
        }
    }
}