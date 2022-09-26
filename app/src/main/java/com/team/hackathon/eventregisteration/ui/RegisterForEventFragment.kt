package com.team.hackathon.eventregisteration.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentRegisterForEventBinding
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel
import org.json.JSONException
import org.json.JSONObject


class RegisterForEventFragment : Fragment() {
    private val binding by lazy { FragmentRegisterForEventBinding.inflate(layoutInflater) }
    private val viewModel: EventRegistrationViewModel by activityViewModels()
    private val db = Firebase.firestore


    companion object {
        const val emailMessage =
            "Hey You Have Been Invited For a Event. You can download app and get further details.\n"
        const val gitLink = "https://github.com/tahir19-fm/Tech-Gala"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.teamMemeberOne2.visibility = View.GONE
        binding.teamMemeberOne3.visibility = View.GONE
        binding.teamMemeberOne4.visibility = View.GONE
        binding.inviteButton1.setOnClickListener {
            binding.teamMemeberOne2.visibility = View.VISIBLE
            binding.inviteButton1.visibility = View.GONE
        }
        binding.inviteButton2.setOnClickListener {
            binding.teamMemeberOne3.visibility = View.VISIBLE
            binding.inviteButton2.visibility = View.GONE
        }
        binding.inviteButton3.setOnClickListener {
            binding.teamMemeberOne4.visibility = View.VISIBLE
            binding.inviteButton3.visibility = View.GONE
        }
        binding.deleteSecondMember.setOnClickListener {
            binding.teamMemeberOne2.visibility = View.GONE
            binding.inviteButton1.visibility = View.VISIBLE
        }
        binding.deleteThirdMember.setOnClickListener {
            binding.teamMemeberOne3.visibility = View.GONE
            binding.inviteButton2.visibility = View.VISIBLE
        }
        binding.deleteFourthMember.setOnClickListener {
            binding.teamMemeberOne4.visibility = View.GONE
            binding.inviteButton3.visibility = View.VISIBLE
        }
        binding.saveButton.setOnClickListener {
            sendEmail()
        }
        binding.bottomButton.setOnClickListener {
            val amt = viewModel.paymentRupees.value.toString()
            val amount = Math.round(amt.toFloat() * 100)
            val checkout = Checkout()

            checkout.setKeyID("rzp_test_lDBxxnlPFiPUGx")

            checkout.setImage(R.drawable.add_icon)
            val obj = JSONObject()
            try {
                obj.put("name", "Tech-A-Thon")

                // put description
                obj.put("description", "Test payment")

                // to set theme color
                obj.put("theme.color", "")

                // put the currency
                obj.put("currency", "INR")

                // put amount
                obj.put("amount", amount)

                // put mobile number
                obj.put(
                    "prefill.contact",
                    FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
                )

                // put email
                obj.put("prefill.email", "techathon@gmail.com")

                checkout.open(requireActivity(), obj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    private fun sendEmail() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "$emailMessage $gitLink")
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(sendIntent, "send invite"))
    }

}