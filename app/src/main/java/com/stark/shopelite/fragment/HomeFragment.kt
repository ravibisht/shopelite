package com.stark.shopelite.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.stark.shopelite.R
import com.stark.shopelite.adapter.CategoryAdapter
import com.stark.shopelite.adapter.SliderAdapter
import com.stark.shopelite.databinding.FragmentHomeBinding
import com.stark.shopelite.model.Category
import com.stark.shopelite.model.Slider
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var timer : Timer
    private lateinit var sliderList: MutableList<Slider>
  //  private lateinit var categoryList: MutableList<Category>
    private var slideBannerCurrentPage = 3
    private val DELAY_TIME : Long =3000
    private val PERIOD_TIME : Long =3000



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)



        //binding.categoryRecyclerView.adapter = CategoryAdapter(categoryList)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.categoryRecyclerView.layoutManager = linearLayoutManager

        sliderList = mutableListOf()

        sliderList.add(Slider(R.drawable.ic_add_profile)) //added extra for smooth scrolling

        sliderList.add(Slider(R.drawable.dummy_img))
        sliderList.add(Slider(R.drawable.ic_email_send))
        sliderList.add(Slider(R.drawable.my_logo))
        sliderList.add(Slider(R.drawable.ic_add_profile))

        sliderList.add(Slider(R.drawable.dummy_img))       //added extra for smooth scrolling

        binding.slideBannerViewpager.adapter = SliderAdapter(sliderList)
        binding.slideBannerViewpager.clipToPadding = false
        binding.slideBannerViewpager.setPageTransformer(MarginPageTransformer(20))
        binding.slideBannerViewpager.currentItem = 1


        binding.slideBannerViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                slideBannerCurrentPage = position
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    slideBannerPageLopper()
                }
                super.onPageScrollStateChanged(state)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startBannerSlideShow()
    }

    private fun slideBannerPageLopper() {

        if (slideBannerCurrentPage == sliderList.size - 1) {
            slideBannerCurrentPage = 1
            binding.slideBannerViewpager.setCurrentItem(slideBannerCurrentPage, false)
        }
        if (slideBannerCurrentPage == 0) {
            slideBannerCurrentPage = sliderList.size - 2
            binding.slideBannerViewpager.setCurrentItem(slideBannerCurrentPage, false)
        }
    }

    private fun startBannerSlideShow(){
        val handler =  Handler()
        val runable = Runnable {
            if (slideBannerCurrentPage >=  sliderList.size )
                slideBannerCurrentPage=1
            binding.slideBannerViewpager.currentItem = slideBannerCurrentPage++
        }

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runable)
            }
        }, DELAY_TIME,PERIOD_TIME)
    }
}


