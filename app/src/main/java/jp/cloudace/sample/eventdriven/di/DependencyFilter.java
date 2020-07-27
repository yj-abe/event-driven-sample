package jp.cloudace.sample.eventdriven.di;

import com.google.inject.servlet.GuiceFilter;

import javax.servlet.annotation.WebFilter;


@WebFilter(filterName = "DependencyFilter", urlPatterns = "/*")
public class DependencyFilter extends GuiceFilter {
}
