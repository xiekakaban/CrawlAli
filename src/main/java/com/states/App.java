package com.states;

import com.states.main.Crawler;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Crawler crawler = new Crawler();
        crawler.setFetchUrlList(Arrays.asList("https://detail.1688.com/offer/536685049415.html?spm=a2615.2177701.0.0.VqHpf2"));
        crawler.fetch();
    }
}
