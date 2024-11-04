package com.example.net_flicks.ui.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.net_flicks.R
import com.example.net_flicks.databinding.FragmentDashboardBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private var activeButton: Button? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var googleMap: GoogleMap? = null  // GoogleMap 객체 선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // MapFragment 초기화 및 onMapReady 호출
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // map_bottom_sheet.xml에 있는 whereIsHere TextView 참조
        val whereIsHereTextView = binding.bottomSheetInclude.root.findViewById<TextView>(R.id.whereIsHere)

        // AutocompleteSupportFragment 초기화
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment

        // 반환할 장소 데이터 필드 설정
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // 장소 선택 리스너 설정
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Toast로 선택된 장소 정보 화면에 표시
                Toast.makeText(requireContext(), "Place: ${place.name}", Toast.LENGTH_LONG).show()

                // 선택된 장소로 지도 이동
                val latLng = place.latLng
                if (latLng != null) {
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }

                // map_bottom_sheet.xml의 whereIsHere TextView의 텍스트를 변경
                whereIsHereTextView.text = place.name
            }

            override fun onError(status: Status) {
                // 상태 코드가 "RESULT_CANCELED"인 경우 메시지를 표시하지 않음
                if (status.statusCode != Status.RESULT_CANCELED.statusCode) {
                    // 그 외의 에러만 Toast로 표시
                    Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
                    Log.e("DashboardFragment", "An error occurred: $status")
                }
            }
        })

        // 버튼 3개 활성화 및 비활성화 함수
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

    override fun onMapReady(map: GoogleMap) { // onMapReady 메서드 추가
        googleMap = map

        // 폴리곤을 표시할 좌표 리스트 (예제 좌표)
        val polygonCoordinates = listOf(
            LatLng(35.1587, 129.1603), // 부산 해상 좌표 (예제)
            LatLng(35.1557, 129.1647),
            LatLng(35.1512, 129.1628),
            LatLng(35.1530, 129.1584),
            LatLng(35.1587, 129.1603) // 시작 좌표와 동일하게 닫기
        )

        // 폴리곤 옵션 설정
        val polygonOptions = PolygonOptions()
            .addAll(polygonCoordinates)
            .strokeColor(ContextCompat.getColor(requireContext(), R.color.redInMap)) // 테두리 색상
            .fillColor(ContextCompat.getColor(requireContext(), R.color.lightRedInMap)) // 채우기 색상
            .strokeWidth(5f) // 테두리 두께

        // 폴리곤 추가
        val polygon = googleMap?.addPolygon(polygonOptions)

        // 폴리곤의 중심 좌표 계산
        val centerLat = polygonCoordinates.map { it.latitude }.average()
        val centerLng = polygonCoordinates.map { it.longitude }.average()
        val centerLatLng = LatLng(centerLat, centerLng)

        // 동적으로 TextView 생성
        val textView = TextView(requireContext()).apply {
            text = "폐어구 밀집 예상 구역"
            setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTypeface(typeface, Typeface.BOLD)
        }

        // Fragment의 FrameLayout에 TextView 추가
        (binding.root as ViewGroup).addView(textView)

        // TextView 위치 업데이트 함수
        fun updateTextViewPosition() {
            val screenPosition = googleMap?.projection?.toScreenLocation(centerLatLng)
            if (screenPosition != null) {
                textView.x = screenPosition.x.toFloat() - textView.width / 2
                textView.y = screenPosition.y.toFloat() - textView.height / 2
            }
        }

        // 초기 위치 설정
        googleMap?.setOnMapLoadedCallback {
            updateTextViewPosition()
        }

        // 카메라 이동 리스너 설정
        googleMap?.setOnCameraMoveListener {
            updateTextViewPosition()
        }

//        // 중심에 텍스트를 표시할 마커 추가
//        googleMap?.addMarker(
//            MarkerOptions()
//                .position(centerLatLng)
//                .title("폐어구 밀집 예상 구역") // 원하는 텍스트
//        )

        // 지도 카메라 이동
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, 12f))
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