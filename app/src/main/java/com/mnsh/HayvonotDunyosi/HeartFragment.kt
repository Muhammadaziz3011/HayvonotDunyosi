package com.mnsh.HayvonotDunyosi

import Adapters.RvAdapter
import Adapters.RvItemClick
import DB.MyDbHelper
import Models.Hayvon
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mnsh.HayvonotDunyosi.databinding.FragmentHeartBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HeartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentHeartBinding
    lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeartBinding.inflate(LayoutInflater.from(context))


        return binding.root
    }


lateinit var myDbHelper:MyDbHelper
    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(context)
        val list = myDbHelper.getAllLabel()
        val likeList = ArrayList<Hayvon>()
        for (hayvon in list) {
            if (hayvon.like == 1){
                likeList.add(hayvon)
            }
        }
        rvAdapter = RvAdapter(likeList, object : RvItemClick{
            override fun edit(belgi: Hayvon) {
                findNavController().navigate(R.id.editFragment, bundleOf("label" to belgi))
            }

            override fun delete(belgi: Hayvon) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("You agree?")
                dialog.setMessage("${belgi.name} Do you really want to delete it??")
                dialog.setPositiveButton("Yes"
                ) { dialog, which ->
                    myDbHelper.deleteLabel(belgi)
                    onResume()
                }

                dialog.setNegativeButton( "No"
                ) { dialog, which ->
                    dialog.cancel()
                }
                dialog.show()
            }

            override fun like(belgi: Hayvon) {
                if (belgi.like == 0) {
                    belgi.like = 1
                } else {
                    belgi.like = 0
                }
                myDbHelper.editLabel(belgi)
                onResume()
            }

            override fun itemClick(belgi: Hayvon) {
                findNavController().navigate(R.id.showBelgiFragment, bundleOf("keyBelgi" to belgi))
            }
        })
        binding.rvHeart.adapter = rvAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HeartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}