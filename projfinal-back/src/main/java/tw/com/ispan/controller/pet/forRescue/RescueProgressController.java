package tw.com.ispan.controller.pet.forRescue;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.service.pet.forRescue.RescueProgressService;


@RestController
@RequestMapping(path = { "/RescueCase//rescueProgress" })
public class RescueProgressController {
	
	
	  @Autowired
	    private RescueProgressService rescueProgressService;
	

}
