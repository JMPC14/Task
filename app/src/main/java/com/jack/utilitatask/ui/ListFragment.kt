package com.jack.utilitatask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.utilitatask.R
import com.jack.utilitatask.api.UtilitaApi
import com.jack.utilitatask.databinding.ListFragmentBinding
import com.jack.utilitatask.databinding.StatusItemBinding
import com.jack.utilitatask.model.UtilitaStatus
import com.jack.utilitatask.viewmodel.ListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListFragment: Fragment() {

    private val binding by viewBinding(ListFragmentBinding::bind)
    private lateinit var api: UtilitaApi
    private lateinit var listViewModel: ListViewModel
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ListFragmentBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://private-176645-utilita.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(UtilitaApi::class.java)

        listViewModel.getPosts()

        with (binding) {
            recyclerViewList.adapter = adapter
            recyclerViewList.layoutManager = LinearLayoutManager(view.context)

            if (adapter.itemCount == 0) {
                listViewModel.dbs.observe(viewLifecycleOwner, {
                    it.forEach { (t, u) ->
                        adapter.add(ListItem(t, u))
                    }
                })

                listViewModel.sites.observe(viewLifecycleOwner, {
                    it.forEach { (t, u) ->
                        adapter.add(ListItem(t, u))
                    }
                })

                adapter.clear()
            }
        }
    }

    inner class ListItem(
        private val title: String,
        private val status: UtilitaStatus
    ) : BindableItem<StatusItemBinding>() {
        override fun bind(viewBinding: StatusItemBinding, position: Int) {
            with(viewBinding) {
                textViewStatus.text = title

                root.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToServiceFragment(title, status)
                    findNavController().navigate(action)
                }
            }
        }

        override fun getLayout(): Int = R.layout.status_item

        override fun initializeViewBinding(view: View): StatusItemBinding {
            return StatusItemBinding.bind(view)
        }
    }
}