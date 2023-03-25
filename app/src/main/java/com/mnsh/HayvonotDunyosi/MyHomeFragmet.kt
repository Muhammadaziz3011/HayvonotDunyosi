package com.mnsh.HayvonotDunyosi

import Adapters.RvItemClick
import Adapters.ViewPager2HayvonAdapter
import DB.MyDbHelper
import Models.Hayvon
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mnsh.HayvonotDunyosi.databinding.FragmentMyHomeFragmetBinding
import kotlinx.android.synthetic.main.tab_item_category.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyHomeFragmet : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentMyHomeFragmetBinding
    lateinit var viewPager2BelgiAdapter: ViewPager2HayvonAdapter
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyHomeFragmetBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "MyHomeFragment")
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "MyHomeFragment")
        myDbHelper = MyDbHelper(context)
        viewPager2BelgiAdapter = ViewPager2HayvonAdapter(context, object : RvItemClick {
            override fun edit(belgi: Hayvon) {
                findNavController().navigate(R.id.editFragment, bundleOf("label" to belgi, "adapter" to viewPager2BelgiAdapter))
            }

            override fun delete(hayvon: Hayvon) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Are you agree?")
                dialog.setMessage("${hayvon.name} Delete?")
                dialog.setPositiveButton("Yes"
                ) { dialog, which ->
                    myDbHelper.deleteLabel(hayvon)
                    onResume()
                }

                dialog.setNegativeButton("No"
                ) { dialog, which ->
                    dialog.cancel()
                }
                dialog.show()
            }

            override fun like(hayvon: Hayvon) {
                if (hayvon.like == 0) {
                    hayvon.like = 1
                } else {
                    hayvon.like = 0
                }
                myDbHelper.editLabel(hayvon)
                onResume()
            }

            override fun itemClick(hayvon: Hayvon) {
                findNavController().navigate(R.id.showBelgiFragment, bundleOf("keyBelgi" to hayvon))
            }
        })
        binding.viewPagerBelgilar.adapter = viewPager2BelgiAdapter


        binding.tabCategory.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val customView = tab?.customView
                    customView?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.tab_fon_selected)
                    customView?.txt_tab_item?.setTextColor(resources.getColor(R.color.kok))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val customView = tab?.customView
                    customView?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.tab_fon_unselected)
                    customView?.txt_tab_item?.setTextColor(resources.getColor(R.color.white))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

        val tabList = ArrayList<String>()
        tabList.add("Yerdagi havonlar")
        tabList.add("Suv hayvonlari")
        tabList.add("Hashorotlar")
        tabList.add("MikroJonzodlar")
        TabLayoutMediator(
            binding.tabCategory, binding.viewPagerBelgilar
        )
        { tab, position ->
            val tabView =
                LayoutInflater.from(context).inflate(R.layout.tab_item_category, null, false)

            tab.customView = tabView

            tabView.txt_tab_item.text = tabList[position]

            binding.viewPagerBelgilar.setCurrentItem(tab.position, true)
        }.attach()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("result", "onActivityResult myHomeFragment")
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyHomeFragmet().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}