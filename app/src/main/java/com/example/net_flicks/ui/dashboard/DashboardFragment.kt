package com.example.net_flicks.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.net_flicks.R
import com.example.net_flicks.databinding.FragmentDashboardBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private var activeButton: Button? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // AutocompleteSupportFragment 초기화
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment

        // 반환할 장소 데이터 필드 설정
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // 장소 선택 리스너 설정
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Toast로 선택된 장소 정보 화면에 표시
                Toast.makeText(requireContext(), "Place: ${place.name}", Toast.LENGTH_LONG).show()
            }

            override fun onError(status: Status) {
                // Toast로 오류 메시지 화면에 표시
                Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
                Log.e("DashboardFragment", "An error occurred: $status")
            }
        })

        setupButtonListeners()

        // Bottom Sheet 초기화
        setupBottomSheet()

        binding.fab1.setOnClickListener { view ->
            Snackbar.make(binding.root, "Here's a Snackbar1", Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab1) // FAB 위에 Snackbar 표시
                .show()

            // 나중에 여기에다가 fab 아이콘 눌렀을 때 작동할 동작 구현
        }

        binding.fab2.setOnClickListener { view ->
            Snackbar.make(binding.root, "Here's a Snackbar1", Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab2) // FAB 위에 Snackbar 표시
                .show()

            // 나중에 여기에다가 fab 아이콘 눌렀을 때 작동할 동작 구현
        }

        binding.fab3.setOnClickListener { view ->
            Snackbar.make(binding.root, "Here's a Snackbar1", Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab3) // FAB 위에 Snackbar 표시
                .show()

            // 나중에 여기에다가 fab 아이콘 눌렀을 때 작동할 동작 구현
        }


        return root
    }

    private fun setupButtonListeners() {
        val buttons = listOf(binding.mapButton1, binding.mapButton2, binding.mapButton3)

        buttons.forEach { button ->
            button.setOnClickListener {
                if (activeButton == button) {
                    // 같은 버튼을 다시 클릭한 경우, 초기 상태로 되돌림
                    resetButton(button)
                    activeButton = null
                } else {
                    // 다른 버튼이 클릭되면 이전 버튼은 비활성화하고 현재 버튼 활성화
                    activeButton?.let { resetButton(it) }
                    setActiveButton(button)
                    activeButton = button
                }
            }
        }
    }

    private fun setActiveButton(button: Button) {
        button.setBackgroundResource(R.drawable.map_rounded_corner_shape_blue)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun resetButton(button: Button) {
        button.setBackgroundResource(R.drawable.map_rounded_corner_shape_white)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun setupBottomSheet() {
        // BottomSheetBehavior 가져오기
        val bottomSheetLayout = binding.bottomSheetInclude.root as LinearLayout
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)

        // 초기 상태 설정
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // 버튼 클릭 리스너 설정
        setupBottomSheetButtons()

        // 상태 변경 리스너 추가 (옵션)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // 완전히 펼쳐졌을 때
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // 최소화되었을 때
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 중일 때 수행할 작업
            }
        })
    }

    private fun setupBottomSheetButtons() {
//        // 확장 버튼
//        binding.bottomSheetInclude.expandPersistentButton.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }
//
//        // 숨기기 버튼
//        binding.bottomSheetInclude.hidePersistentButton.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//        }
//
//        // 모달 바텀시트 열기 버튼 (모달 바텀시트 구현 필요)
//        binding.bottomSheetInclude.showModalButton.setOnClickListener {
//            // 모달 바텀시트 열기 로직 추가
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}