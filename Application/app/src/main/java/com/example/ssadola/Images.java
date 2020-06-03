/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ssadola;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

    /**
     * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb API should be
     * used to fetch the URLs.
     *
     * Credit to Romain Guy for the photos:
     * http://www.curious-creature.org/
     * https://plus.google.com/109538161516040592207/about
     * http://www.flickr.com/photos/romainguy
     */
    public final static String[] imageUrls = new String[] {
            "https://thumb-wishbeen.akamaized.net//JVqb1ksoQyD-551RrGv5GiulMmI=//880x//smart//filters:no_upscale()//img-wishbeen.akamaized.net//post//1582865198724_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.55.16.png",
            "https://thumb-wishbeen.akamaized.net/3RaGFWUBrZU0Voy9y4_3ybOJE6g=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582865486062_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.45.43.png",
            "https://thumb-wishbeen.akamaized.net/UW9rok1uHZlvwbcxvvdbP-y7DrQ=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582866350476_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.32.52-1.png",
            "https://thumb-wishbeen.akamaized.net/cTRtN1ZC-x73MyZ-ydmaAqbHDIM=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582866731373_sunyeo.jpg",
            "https://thumb-wishbeen.akamaized.net/pjWeZhH1m9ByrJICXvUIfGKiYYQ=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868538256_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.15.58.png",
            "https://thumb-wishbeen.akamaized.net/GmpVqCz4aR-gVSKSCEj6PP-sO8Q=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868589789_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_8.10.35.png",
            "https://thumb-wishbeen.akamaized.net/oOGVn7BoZIHYdSAcNZrH2A7BB4M=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868729174_008.jpg",
            "http://www.wetravel.kr/data/editor/1807/thumb-20180706171612_e2390b530898900acc6f3e23b0adf161_inoz_600x393.png",
            "https://1.bp.blogspot.com/-gohXthjKiLA/W8FZMFCm6dI/AAAAAAAAFys/XFub9lVnUtUTM1el1hV2eRzmOvlcLUICACLcBGAs/s1600/10.jpg",
            "https://t1.daumcdn.net/cfile/tistory/25713A4C58AA94801C",
            "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EB%A6%AC%ED%8B%80+%ED%8F%AC%EB%A0%88%EC%8A%A4%ED%8A%B8#",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fmblogthumb-phinf.pstatic.net%2FMjAyMDAyMjNfMTAg%2FMDAxNTgyNDUwMDgyOTY4.W90_Ty5-q762l9uKHKnx9QG2jm_FCX00G_K977McAoUg.e4Ua3DTN3cujMdUOMBrk-y4PcMxKQKdHh0SOHD34Ab8g.PNG.guramy46%2FIMG_6875.PNG%3Ftype%3Dw800&imgrefurl=https%3A%2F%2Fm.blog.naver.com%2Fguramy46%2F221823069691&tbnid=d0diI-WXvUNMsM&vet=12ahUKEwifhLLs_NDpAhUWAqYKHV67CI4QMygIegUIARDfAQ..i&docid=kHZsB0vXt5cBMM&w=800&h=450&q=%EC%9D%B4%ED%83%9C%EC%9B%90%20%ED%81%B4%EB%9D%BC%EC%93%B0%20%EB%8B%A8%EB%B0%A42%ED%98%B8&ved=2ahUKEwifhLLs_NDpAhUWAqYKHV67CI4QMygIegUIARDfAQ",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfNiAg/MDAxNTY1OTI4MTMyOTc0.UyT6GuKxN5mAwjUClcsqBRp76X_M50o-gANyWYd_Ey0g.6_U1Tmptws1oBSUnk3vvQjO1o-CzC-FQKl6Utmkl-Gog.JPEG/3.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfOTEg/MDAxNTY1OTMyNjI0MjMx.mGMT_cqnxcNXTMV-7qLmVmffsjXF0uDc6cXrVJH3R4Eg.RbZEy6Za-paoEkjl1Bd-LQC0o33hiXODbCZ7FycCGSAg.JPEG/6.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfNDMg/MDAxNTY1OTMzNjk1MjY3.CHAmEEt6uBsQ1qXz4XYhnY_MkhjRwsO28Hpqvn7bdlYg.YYkKPT_CA5phTrs3mm44VuZRZLj5thEln_OK9Gaj8j8g.JPEG/18.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfOCAg/MDAxNTY1OTM0MTg5NjQz.kbIluuI1oRX4MabNSnquMuZ2WoveB_gM-Mev13-UX0Ug.DqBl3HLneovT-Cy2SPcbpOmC2C5p5qsITu81mhmK044g.JPEG/23.jpg?type=w1200",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSKbbOdsIshSlxnyt3aujRJCDWoMhfCBdDYRz9bSCGJM7svL_0M&usqp=CAU",
            "https://t4.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2V9Z/image/d-XbhX_J54LzACTQMo7jdrgnopE.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQad2Dq28DPI7RMGJ2b7inKSjlVqazuzJbtYc90_cyekqxOQkTA&usqp=CAU",
            "https://i1.wp.com/s3-ap-northeast-2.amazonaws.com/blog.allstay.com/wp-content/uploads/2020/04/20161659/5e97f889e3a6c71e7c2b77bf-p.jpg?fit=740%2C494&ssl=1",
            "https://postfiles.pstatic.net/MjAxOTEwMjRfMjQ2/MDAxNTcxODk4ODMxNTcx.ccTotCgmYnvySZse-d2kYx2ZtGyBvCOW14LgYF4l3lQg.k_9MH4qrSkMwJu2EiFuKYdydpUDA3ERz-ws7JSydpUwg.JPEG.gongjubookcamp/%EA%B3%B5%EC%A3%BC%EB%B6%81%EC%BA%A0%ED%94%84_%EC%8B%A0%EC%84%9C%EC%9C%A0%EA%B8%B07_%EC%B4%AC%EC%98%81%EC%A7%80_2.jpg?type=w773"




    };

    /**
     * This are PicasaWeb thumbnail URLs and could potentially change. Ideally the PicasaWeb API
     * should be used to fetch the URLs.
     *
     * Credit to Romain Guy for the photos:
     * http://www.curious-creature.org/
     * https://plus.google.com/109538161516040592207/about
     * http://www.flickr.com/photos/romainguy
     */
    public final static String[] imageThumbUrls = new String[] {
            "https://thumb-wishbeen.akamaized.net//JVqb1ksoQyD-551RrGv5GiulMmI=//880x//smart//filters:no_upscale()//img-wishbeen.akamaized.net//post//1582865198724_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.55.16.png",
            "https://thumb-wishbeen.akamaized.net/3RaGFWUBrZU0Voy9y4_3ybOJE6g=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582865486062_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.45.43.png",
            "https://thumb-wishbeen.akamaized.net/UW9rok1uHZlvwbcxvvdbP-y7DrQ=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582866350476_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.32.52-1.png",
            "https://thumb-wishbeen.akamaized.net/cTRtN1ZC-x73MyZ-ydmaAqbHDIM=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582866731373_sunyeo.jpg",
            "https://thumb-wishbeen.akamaized.net/pjWeZhH1m9ByrJICXvUIfGKiYYQ=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868538256_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.15.58.png",
            "https://thumb-wishbeen.akamaized.net/GmpVqCz4aR-gVSKSCEj6PP-sO8Q=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868589789_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2020-02-13_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_8.10.35.png",
            "https://thumb-wishbeen.akamaized.net/oOGVn7BoZIHYdSAcNZrH2A7BB4M=/880x/smart/filters:no_upscale()/img-wishbeen.akamaized.net/post/1582868729174_008.jpg",
            "http://www.wetravel.kr/data/editor/1807/thumb-20180706171612_e2390b530898900acc6f3e23b0adf161_inoz_600x393.png",
            "https://1.bp.blogspot.com/-gohXthjKiLA/W8FZMFCm6dI/AAAAAAAAFys/XFub9lVnUtUTM1el1hV2eRzmOvlcLUICACLcBGAs/s1600/10.jpg",
            "https://t1.daumcdn.net/cfile/tistory/25713A4C58AA94801C",
            "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EB%A6%AC%ED%8B%80+%ED%8F%AC%EB%A0%88%EC%8A%A4%ED%8A%B8#",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fmblogthumb-phinf.pstatic.net%2FMjAyMDAyMjNfMTAg%2FMDAxNTgyNDUwMDgyOTY4.W90_Ty5-q762l9uKHKnx9QG2jm_FCX00G_K977McAoUg.e4Ua3DTN3cujMdUOMBrk-y4PcMxKQKdHh0SOHD34Ab8g.PNG.guramy46%2FIMG_6875.PNG%3Ftype%3Dw800&imgrefurl=https%3A%2F%2Fm.blog.naver.com%2Fguramy46%2F221823069691&tbnid=d0diI-WXvUNMsM&vet=12ahUKEwifhLLs_NDpAhUWAqYKHV67CI4QMygIegUIARDfAQ..i&docid=kHZsB0vXt5cBMM&w=800&h=450&q=%EC%9D%B4%ED%83%9C%EC%9B%90%20%ED%81%B4%EB%9D%BC%EC%93%B0%20%EB%8B%A8%EB%B0%A42%ED%98%B8&ved=2ahUKEwifhLLs_NDpAhUWAqYKHV67CI4QMygIegUIARDfAQ",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfNiAg/MDAxNTY1OTI4MTMyOTc0.UyT6GuKxN5mAwjUClcsqBRp76X_M50o-gANyWYd_Ey0g.6_U1Tmptws1oBSUnk3vvQjO1o-CzC-FQKl6Utmkl-Gog.JPEG/3.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfOTEg/MDAxNTY1OTMyNjI0MjMx.mGMT_cqnxcNXTMV-7qLmVmffsjXF0uDc6cXrVJH3R4Eg.RbZEy6Za-paoEkjl1Bd-LQC0o33hiXODbCZ7FycCGSAg.JPEG/6.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfNDMg/MDAxNTY1OTMzNjk1MjY3.CHAmEEt6uBsQ1qXz4XYhnY_MkhjRwsO28Hpqvn7bdlYg.YYkKPT_CA5phTrs3mm44VuZRZLj5thEln_OK9Gaj8j8g.JPEG/18.jpg?type=w1200",
            "https://post-phinf.pstatic.net/MjAxOTA4MTZfOCAg/MDAxNTY1OTM0MTg5NjQz.kbIluuI1oRX4MabNSnquMuZ2WoveB_gM-Mev13-UX0Ug.DqBl3HLneovT-Cy2SPcbpOmC2C5p5qsITu81mhmK044g.JPEG/23.jpg?type=w1200",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSKbbOdsIshSlxnyt3aujRJCDWoMhfCBdDYRz9bSCGJM7svL_0M&usqp=CAU",
            "https://t4.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2V9Z/image/d-XbhX_J54LzACTQMo7jdrgnopE.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQad2Dq28DPI7RMGJ2b7inKSjlVqazuzJbtYc90_cyekqxOQkTA&usqp=CAU",
            "https://i1.wp.com/s3-ap-northeast-2.amazonaws.com/blog.allstay.com/wp-content/uploads/2020/04/20161659/5e97f889e3a6c71e7c2b77bf-p.jpg?fit=740%2C494&ssl=1",
            "https://postfiles.pstatic.net/MjAxOTEwMjRfMjQ2/MDAxNTcxODk4ODMxNTcx.ccTotCgmYnvySZse-d2kYx2ZtGyBvCOW14LgYF4l3lQg.k_9MH4qrSkMwJu2EiFuKYdydpUDA3ERz-ws7JSydpUwg.JPEG.gongjubookcamp/%EA%B3%B5%EC%A3%BC%EB%B6%81%EC%BA%A0%ED%94%84_%EC%8B%A0%EC%84%9C%EC%9C%A0%EA%B8%B07_%EC%B4%AC%EC%98%81%EC%A7%80_2.jpg?type=w773"
    };
}
