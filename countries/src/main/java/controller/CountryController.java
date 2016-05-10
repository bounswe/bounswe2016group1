package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import dao.CountryDAO;
import getter.CountryGetter;
import model.CountryList;
import model.CountryModel;

@Controller
public class CountryController {
	
	@Autowired
	private CountryDAO countryDAO;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView modelAndView) throws IOException, ParserConfigurationException, SAXException{
		ArrayList<CountryModel> countryList = CountryGetter.show();
		modelAndView.addObject("countryList", countryList);
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public ModelAndView addToDB(ModelAndView modelAndView, @ModelAttribute CountryList countryName) throws IOException, ParserConfigurationException, SAXException{
		ArrayList<CountryModel> countries = CountryGetter.getByName(countryName.getCountries());
		for(int i=0; i<countries.size(); i++){
			countryDAO.add(countries.get(i));
		}
		ArrayList<CountryModel> countryList = CountryGetter.getOthers();
		modelAndView.addObject("countryList", countryList);
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
