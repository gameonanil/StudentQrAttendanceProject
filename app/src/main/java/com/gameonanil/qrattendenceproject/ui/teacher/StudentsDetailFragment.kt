package com.gameonanil.qrattendenceproject.ui.teacher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.gameonanil.qrattendenceproject.R

import com.gameonanil.qrattendenceproject.databinding.FragmentStudentsDetailBinding
import com.gameonanil.qrattendenceproject.model.User
import com.gameonanil.qrattendenceproject.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class StudentsDetailFragment : Fragment() {
    companion object{
        private const val TAG = "StudentsDetailFragment"
    }

    private var _binding: FragmentStudentsDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var currentUser : User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsDetailBinding.inflate(layoutInflater,container,false)

        val navHostFragment = NavHostFragment.findNavController(this);
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainTeacherFragment,
            )
        )
        NavigationUI.setupWithNavController(binding.toolbarStudentDetail, navHostFragment,appBarConfiguration)

        /** TO USE OPTIONS MENU*/
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarStudentDetail)
        binding.toolbarStudentDetail.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        currentUser = StudentsDetailFragmentArgs.fromBundle(requireArguments()).userDetail
        Log.d(TAG, "onCreateView: USER!!!!!!!!!!!!!!!!!!${currentUser.username}")

        binding.apply {
            currentUser.username?.let { tvUserName.text = "User Name: $it" }
            currentUser.email?.let { tvEmail.text = "Email: $it" }
            

        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemLogout) {

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}