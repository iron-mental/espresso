package com.iron.espresso.presentation.profile.edit

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditAreaBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAreaFragment :
    BaseFragment<FragmentEditAreaBinding>(R.layout.fragment_edit_area) {

    private val editAreaViewModel by viewModels<EditAreaViewModel>()
    private val areaAdapter by lazy {
        AreaAdapter { address ->
            when (editAreaViewModel.pickStep) {
                PickStep.STEP_1 -> {
                    editAreaViewModel.step1.value = address
                    editAreaViewModel.setStep2List(address)
                    binding.addressStep1.isSelected = false
                    binding.addressStep2.isSelected = true
                }
                PickStep.STEP_2, PickStep.DONE -> {
                    editAreaViewModel.step2.value = address
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle(R.string.title_edit_area)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.run {
            this.viewModel = editAreaViewModel
            addressList.adapter = areaAdapter
            addressList.itemAnimator = null
            addressStep1.setOnClickListener {
                editAreaViewModel.step1.value = ""
                editAreaViewModel.step2.value = ""
                editAreaViewModel.setStep1List()
                addressStep1.isSelected = true
                addressStep2.isSelected = false
            }

            arguments?.run {
                val sido =  getString(ARG_SIDO).orEmpty()
                val siGungu = getString(ARG_SI_GUNGU).orEmpty()

                editAreaViewModel.step1.value = sido
                editAreaViewModel.step2.value = siGungu

                if (editAreaViewModel.pickStep == PickStep.STEP_1) {
                    addressStep1.isSelected = true
                } else {
                    addressStep2.isSelected = true
                }
            }
        }
    }

    private fun setupViewModel() {
        editAreaViewModel.run {
            addressList.observe(viewLifecycleOwner, { list ->
                areaAdapter.submitList(list)
            })

            successEvent.observe(viewLifecycleOwner, EventObserver { isSuccess ->
                if (isSuccess) {
                    toast(R.string.success_modify)
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
                }
                activity?.onBackPressed()
            })

            toastMessage.observe(viewLifecycleOwner, EventObserver(::toast))

            loadingState.observe(viewLifecycleOwner, EventObserver(::setLoading))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        setMenuItems(menu)
    }

    private fun setMenuItems(menu: Menu) {
        val groupId = 0
        val set = MenuSet.ICON_DONE

        if (menu.findItem(set.menuId) == null) {
            menu.add(groupId, set.menuId, 0, set.titleResId)
                .setIcon(ContextCompat.getDrawable(requireContext(), set.imageResId))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_done -> {
                editAreaViewModel.modifyInfo()
            }
            else -> {

            }
        }
        return true
    }

    companion object {
        private const val ARG_SIDO = "sido"
        private const val ARG_SI_GUNGU = "si_gungu"

        fun newInstance(sido: String, siGungu: String) =
            EditAreaFragment().apply {
                arguments = bundleOf(ARG_SIDO to sido, ARG_SI_GUNGU to siGungu)
            }
    }
}


class AreaAdapter(private val itemClick: (String) -> Unit) :
    ListAdapter<String, AreaViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder =
        AreaViewHolder(parent, itemClick)

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}

class AreaViewHolder(parent: ViewGroup, private val itemClick: (String) -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_area, parent, false)
    ) {

    private val text: TextView = itemView.findViewById(R.id.text)

    init {
        itemView.setOnClickListener {
            itemClick(text.text.toString())
        }
    }

    fun bind(text: String) {
        this.text.text = text
    }
}