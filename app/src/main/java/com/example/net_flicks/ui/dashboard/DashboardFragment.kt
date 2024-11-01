package com.example.net_flicks.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.net_flicks.R
import com.example.net_flicks.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Bottom Sheet 초기화
        setupBottomSheet()

        return root
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
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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