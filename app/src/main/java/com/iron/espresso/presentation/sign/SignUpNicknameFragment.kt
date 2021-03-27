package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignUpNicknameBinding
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignUpViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignUpNicknameFragment :
    BaseFragment<FragmentSignUpNicknameBinding>(R.layout.fragment_sign_up_nickname) {

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            vm = signUpViewModel

            inputNickName.requestFocus()
        }
        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner) { type ->
                if (type == CheckType.CHECK_NICKNAME_FAIL) {
                    binding.nicknameField.error = type.message
                }
            }
        }

        compositeDisposable += binding.inputNickName.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ nickname ->
                binding.nicknameField.error = when {
                    nickname.isEmpty()
                        || signUpViewModel.isValidNickName(nickname.toString()) -> null
                    else -> getString(R.string.invalid_nickname)
                }
            }, {

            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_action, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> {
                signUpViewModel.run {
                    verifyNicknameCheck()
                }
            }
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() =
            SignUpNicknameFragment()
    }
}