package com.rginco.urlshortener.urlshortener.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//@Author : Rupali Guleria

@Getter
@Setter
@Entity
public class URLMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longUrl;
    private String shortUrl;
    private int hitCount; // Added hitCount field to track Frequency



}
