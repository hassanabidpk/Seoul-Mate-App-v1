package com.example.seoulmappedia;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.sisfgroupd.seoulmappedia.R;

import android.util.Log;

public class UniversityList {

	public UniversityList() {
		
	}
	
	public static final String NAVER_MAP_API_KEY = "d71437e307f0f08f72430b6a741c396a";
	
	public static final String[] UNI_LIST = {"CAU", "DSU", "DGU", "EWHA", "HYU", "HGU", "HUFS", "KKU", "KU", "KOOKU",  
		"KHU", "SNU", "SKKU", "SOGANG", "SMU", "US", "YSU"};
	
	public static final String[] MAP_NAME = {"Chungang University", "Duksung Women Univ.", "Dongguk University", "Ewha Women Uni.", "Hanyang University", "Hongik University", 
		"HUFS Uni.", "Konkuk University", "Korea University", "Kookmin University", "KyungHee University", "Seoul Nat. University", "SungKyungKwan Uni.", "Sogang University", "SookMyung University",
		"University of Seoul", "Yonsei University", "Halal Restaurants", "SGC Locations", "Real-Estate Locations"};
	public static final int[]  UNI_LIST_NO ={0,1,2,3,4,5,6,7,8,9,10,12,13,14,15,16};
	public static final String[] MAP_LIST = {"caumap.jpg", "dsumap.png", "dgumap.jpg", "ewhamap.jpg", "hanyangmap.png", 
		"hongikmap.png", "hufsmap.png", "kkumap.png", "kumap.png", "kookminmap.png",  
		"khumap.png", "snumap.png", "skkumap.jpeg", "sogangmap.jpg", "smumap.png", "usmap.png", "ysumap.jpg"};
	
	public static final String[] AIRPORT_LIST = {"cauair", "dsuair", "dguair", "ewhaair", "hyuair", 
		"hguair", "hufsair", "kkuair", "kuair", "kookuair",  
		"khuair", "snuair", "skkuair", "sogangair", "smuair", "usair", "ysuair"};
	
	public static final String[] PLACES = {"administration", "airport", "bank",	"burger","bus", "cafetaria",	"clinic",	"coffee",	"convenience store",
		"dentist", "departmental store", "dormitory", "drug store",	"entrance",	"fastfood" ,"gate",	"guest house", "home plus",
		"hospital" ,	"hostel" ,	"hotel" , "library", "lotte", "lotteria" , "mall",	"mcdonalds" ,	
		"motel" , "movie",	"pharmacy" ,	"pizza" ,	"police" ,	"post", "restaurant", "school", "student center", "subway",	"university","halal", "kebab", "mosque", 
		"store", "real estate", "mcdonald" };

	
	public static final String[] PLACES_DRAWABLES = {"administration","airport", "bank","fastfood",	"busstop", "cafetaria",	"hospital",	"coffee","conveniencestore",
		"dentist", "departmentstore"	, "hostel",	"pharmacy", "entrance",	"fastfood", "entrance", "hostel", "departmentstore"	,
		"hospital" ,	"hostel" , "hotel" , "library", "departmentstore",	"fastfood" , "mall", "fastfood" ,	
		"motel" , "cinema",	"pharmacy" ,	"pizzaria" ,	"police" ,	"postal", "restaurant","school", "administration", "subway",	"university", "halal", "kebab",
		"mosque", "departmentstore", "realestate", "fastfood"};
	
	private static final int[] PLC_ARRAY_ID =  {R.array.CAU_Plc_array,R.array.DSU_Plc_array,R.array.DGU_Plc_array,R.array.EWHA_Plc_array,
		R.array.HYU_Plc_array,R.array.HGU_Plc_array, R.array.HUFS_Plc_array, R.array.KKU_Plc_array, R.array.KU_Plc_array, R.array.KOOKU_Plc_array, R.array.KHU_Plc_array,
		R.array.SNU_Plc_array,R.array.SKKU_Plc_array, R.array.SOGANG_Plc_array, R.array.SMU_Plc_array, R.array.US_Plc_array, 
		R.array.YSU_Plc_array, R.array.halal_restaurants, R.array.sgc_locations, R.array.real_locations};
	private static final int[] DESC_ARRAY_ID = {R.array.CAU_desc_array,R.array.DSU_desc_array,R.array.DGU_desc_array,R.array.EWHA_desc_array,
		R.array.HYU_desc_array,R.array.HGU_desc_array, R.array.HUFS_desc_array, R.array.KKU_desc_array, R.array.KU_Plc_desc_array, R.array.KOOKU_desc_array,
		R.array.KHU_desc_array, R.array.SNU_desc_array, R.array.SKKU_desc_array, R.array.SOGANG_desc_array, R.array.SMU_desc_array, R.array.US_desc_array,
		R.array.YSU_desc_array, R.array.halal_restaurants_desc, R.array.sgc_desc, R.array.real_desc};
	
	public static final String[] CAMPUS_URLS = {"http://www.cau.ac.kr/campusmap_eng/campusmap_seoul.htm", "http://www.duksung.ac.kr/eng/about/map.jsp", 
		"http://www.dongguk.edu/mbs/en/subview.jsp?id=en_010600000000", "http://www.ewha.ac.kr/english/html/campusmap_eng/Map.html", 
		"http://www.hanyang.ac.kr/code_html/visual/vr/_hys_tour_eng.htm","http://home.hongik.ac.kr/intro/intro_5_1.php", "http://international.hufs.ac.kr/", 
		"http://www.konkuk.ac.kr/eng/jsp/About/about_060200.jsp", "http://www.korea.edu/", "http://www.kookmin.ac.kr/FUNC/cybertour/cybertour.php?language=english", 
		"http://www.khu.ac.kr/eng/about/map_directions.jsp", "http://www.useoul.edu/upload/about/campusmap_eng_2013.pdf", 
		 "http://www.skku.edu/eng/","http://www.sogang.ac.kr/english/about/07_campus_03.html", "http://e.sookmyung.ac.kr/contents/contents.jsp?cmsCd=CM0317",
		 "http://uos.ac.kr/campustour/campustour_eng.htm", 
		"http://www.yonsei.ac.kr/eng/about/campuses/sinchon/"};
	
	
	public static final String[] MAP_URLS = {"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab47e0c672bf9626&msa=0",
		"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c4b45b812af5a&msa=0"
		,"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa0d3fc5b291b5959&msa=0",
			"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab4f335093a9b48a&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa9f3b9515193eaa7&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa0e7c48a5de67eee&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c10d5413ec7cd&msa=0&iwloc=0004a9f939efda8b84a3c",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa9edddc6a2324275&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941be30aa3f7bde0&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941ca05e4ecdc089&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab552741f13da039&msa=0&ll=37.591757,127.052507&spn=0.006869,0.01604",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004ab5f5ce0415944589&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c233da8e313ae&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab44848dedb07645&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c6e39a2ddfa03&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c7f4df146f3fe&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c7f4df146f3fe&msa=0", // next is halal food restaurants
				"https://www.google.com/maps/ms?msid=206355923113904901373.0004e3cb5b2d1ec400156&msa=0",  // next one SGC Locations
						"https://www.google.com/maps/ms?msid=217819860417471254058.0004aabe0a254344b2a5e&msa=0", // next one is for Real-state
						"https://www.google.com/maps/ms?msid=217819860417471254058.0004aac75217e1b184658&msa=0"};
	
	
	public static final String[] AIRPORT_URLS = {"http://www.airportlimousine.co.kr/route/routee04.htm","http://www.airportlimousine.co.kr/route/routee04.htm"
		,"http://www.kallimousine.com/eng/schedule_en.asp","http://www.airportlimousine.co.kr/route/routee04.htm",
		"http://www.calt.co.kr/common/common.do?jsp_path=user/kor/trans/route_6101&menu_id=trans_0202#.Ug4QmL-pr8s",
		"http://www.airportlimousine.co.kr/route/routee03.htm","http://www.airportlimousine.co.kr/route/routee04.htm",
		"http://www.airportlimousine.co.kr/route/routee13.htm",
		"http://www.calt.co.kr/common/common.do?jsp_path=user/kor/trans/route_6101&menu_id=trans_0202#.Ug4RGL-pr8s",
		"http://www.airportlimousine.co.kr/route/routee04.htm","http://www.airportlimousine.co.kr/route/routee04.htm",
		"http://www.airportlimousine.co.kr/route/routee05.htm","http://www.airportlimousine.co.kr/route/routee04.htm",
		"http://www.airportlimousine.co.kr/route/routee15.htm","http://www.airportlimousine.co.kr/route/routee12.htm",
		"http://www.airportlimousine.co.kr/route/routee03.htm","http://www.airportlimousine.co.kr/route/routee04.htm"};
	
	public String getDrawableName (String title) {
		for(int i=0; i<PLACES.length;i++) {
	//		Log.d("UniversityList", "Title :" + title + " PLACEs: " + PLACES[i]);
			if(title.toLowerCase(Locale.US).contains(PLACES[i]))
				return PLACES_DRAWABLES[i];
		}
			return "nostringfound";
			
		}
	
	public boolean isDrawable(String title) {
		for(int i=0; i<PLACES.length;i++) {
			if(title.toLowerCase(Locale.US).contains(PLACES[i]))
				return true;
		}
			return false;
		
	}
	
	public int getPlcID(int no) {
		return PLC_ARRAY_ID[no];
		
	}
		
	public int getDescID(int no) {
		return DESC_ARRAY_ID[no];
		
	}
	


	
	
}
