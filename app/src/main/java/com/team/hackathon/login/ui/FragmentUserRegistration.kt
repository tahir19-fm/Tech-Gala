package com.dietTracker.invite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.login.util.LoginViewModel
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentUserRegistrationBinding
import com.team.hackathon.login.util.UtilForMonth
import com.team.hackathon.login.util.isValidPhoneNumber
import com.team.hackathon.LoginActivity
import com.team.hackathon.login.data.Address
import com.team.hackathon.login.data.User
import com.team.hackathon.login.data.UserRegistrationDto
import java.util.*

class FragmentUserRegistration : Fragment() {

    var hashMap: HashMap<String, String> = HashMap<String, String>()
    var hashMapDOB: HashMap<String, Int> = HashMap<String, Int>()

    var date: String? = null
    var month: String? = null
    var year: String? = null
    var gender: String? = null
    var country: String? = null
    var dateInt: Int? = null
    var monthInt: Int? = null
    var yearInt: Int? = null

    private val viewModel: LoginViewModel by activityViewModels()
    private val binding by lazy { FragmentUserRegistrationBinding.inflate(layoutInflater) }

    companion object {
        fun getInstance() = FragmentUserRegistration()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ccp = binding.countryCodePicker
        ccp.registerCarrierNumberEditText(binding.phoneNumberEditText)
       // binding.registrationHeader.headerText.text = "REGISTER"
      //  binding.registrationHeader.image.visibility = View.GONE
        val date = resources.getStringArray(R.array.date)
        val arrayAdapterDate = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, date) }
        binding.selectDate.setAdapter(arrayAdapterDate)
        val month = resources.getStringArray(R.array.month)
        val arrayAdapterMonth = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, month) }
        binding.selectMonth.setAdapter(arrayAdapterMonth)
        //binding.selectGender1.setText("Male",false)
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender =
            context?.let { ArrayAdapter(it, R.layout.dropdown_layout, gender) }
        binding.selectGender1.setAdapter(arrayAdapterGender)
        val country = resources.getStringArray(R.array.countries_array)
        val arrayAdapterCountry =
            context?.let { ArrayAdapter(it, R.layout.dropdown_layout, country) }
        binding.selectCountryd.setAdapter(arrayAdapterCountry)
        val years = resources.getStringArray(R.array.years)
        val arrayAdapterYears = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, years) }
        binding.selectYear.setAdapter(arrayAdapterYears)

        //
        // Default Values Please Do Not Remove It
        hashMapDOB.put("date", 12)
        hashMapDOB.put("month", 6)
        hashMapDOB.put("year", 2000)
        hashMap.put("gender", "Male")
        hashMap.put("country", "India")
        //
        // Default Values Please Do Not Remove It


        binding.selectDate.setOnItemClickListener { parent, _, position, _ ->
            this.date = parent.getItemAtPosition(position) as String
            if (date.contains(this.date)) {
                //  Toast.makeText(context, Date, Toast.LENGTH_LONG).show()
                hashMapDOB.put("date", this.date!!.toInt())
            }
        }
        binding.selectMonth.setOnItemClickListener { parent, _, position, _ ->
            this.month = parent.getItemAtPosition(position) as String
            if (month.contains(this.month)) {
                val tt = UtilForMonth.returnMonthNumber(this.month.toString())
                Toast.makeText(context, tt.toString(), Toast.LENGTH_LONG).show()
                hashMapDOB.put("month", tt!!.toInt())
            }
        }
        binding.selectGender1.setOnItemClickListener { parent, _, position, _ ->
            this.gender = parent.getItemAtPosition(position) as String
            if (gender.contains(this.gender)) {
                //  Toast.makeText(context, Gender, Toast.LENGTH_LONG).show()
                hashMap.put("gender", this.gender!!)

            }
        }
        binding.selectCountryd.setOnItemClickListener { parent, _, position, _ ->
            this.country = parent.getItemAtPosition(position) as String
            if (country.contains(this.country)) {
                //  Toast.makeText(context, Country, Toast.LENGTH_LONG).show()
                hashMap.put("country", this.country!!)
            }
        }
        binding.selectYear.setOnItemClickListener { parent, _, position, _ ->
            year = parent.getItemAtPosition(position) as String
            if (years.contains(year)) {
                //  Toast.makeText(context, Year, Toast.LENGTH_LONG).show()
                hashMapDOB.put("year", year!!.toInt())

            }
        }
        binding.phoneNumberEditText.setText(viewModel.phoneNumberRegistration.value)


        binding.registerButton.setOnClickListener {
            if (ccp.isValidFullNumber) {
                binding.progressBarconfig.visibility = View.VISIBLE
                binding.registerButton.visibility = View.INVISIBLE

                val Name = binding.nameEditText.text
                hashMap.put("name", Name.toString())

                binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumberEditText)
                val ccp = binding.countryCodePicker
                ccp.registerCarrierNumberEditText(binding.phoneNumberEditText)
                hashMap.put("phone", binding.countryCodePicker.fullNumberWithPlus)

                if (!isValidPhoneNumber(ccp.fullNumber)) {
                    Toast.makeText(requireContext(), "Not valid number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val City = binding.selectcityDropdown.text
                hashMap.put("city", City.toString())

                dateInt = hashMapDOB.get("date")
                monthInt = hashMapDOB.get("month")
                yearInt = hashMapDOB.get("year")

                val calender = Calendar.getInstance()
                yearInt?.let { it1 ->
                    monthInt?.let { it2 ->
                        dateInt?.let { it3 ->
                            calender.set(
                                it1,
                                it2, it3
                            )
                        }
                    }
                }
                var date = calender.timeInMillis

                hashMap.put("date_of_birth", date.toString())

                viewModel.saveUserDataOnLocal(UserRegistrationDto(User("hjg","sffs","3455","male",12,Address("asdasdas","asdasdsa"))))
                viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)
            } else {
                Toast.makeText(context, "please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }


        binding.selectDate.setSelection(binding.selectDate.length())
        binding.selectMonth.setSelection(binding.selectMonth.length())
        binding.selectYear.setSelection(binding.selectYear.length())
        binding.selectGender1.setSelection(binding.selectGender1.length())
        binding.selectCountryd.setSelection(binding.selectCountryd.length())
        binding.selectcityDropdown.setSelection(binding.selectcityDropdown.length())
    }
    private fun saveUserDataOnLocal(){
        viewModel.saveUserDataOnLocal(
            UserRegistrationDto(
                User(
                "Testing_id",
                hashMap["name"].toString(),
                hashMap["phone"].toString(),
                hashMap["gender"].toString(),
                hashMap["date_of_birth"]!!.toLong(),
                Address(
                    hashMap["country"].toString(),
                    hashMap["city"].toString()
                )
            )
            )
        )

    }

}

