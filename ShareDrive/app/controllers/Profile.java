package controllers;

import models.AppModel;
import models.CarInformation;
import models.User;
import play.mvc.Before;

public class Profile extends Application {
	
	@Before
	static void checkUser() {
		if (connected() == null) {
			flash.error("Please log in first");
			Application.index();
		}
	}
	
	
	public static void changeCarInfo(CarInformation carInfo) {
		User user=connected();
		CarInformation carInfoActual = CarInformation.findByUser(user);
		System.out.println("Anzahl: " + CarInformation.count());
		System.out.println("CIA:" + carInfoActual);
		if(carInfoActual!=null && (carInfo==null || ((carInfo.car==null || carInfo.car.isEmpty()) && carInfo.ageOfCar==0))){
			carInfoActual.delete();
		}
		else if(carInfoActual==null){
			carInfoActual = new CarInformation(carInfo.ageOfCar, carInfo.car, connected());
			carInfo.create();
		}
		else{
			carInfoActual.ageOfCar=carInfo.ageOfCar;
			carInfoActual.car=carInfo.car;
		}
		AppModel unis=new AppModel();
		carInfo=carInfoActual;
		System.out.println("hier CarInfo:" + carInfo);
		renderTemplate("Profile/showCarInfo.html", carInfo, unis);
	}
	
	
	public static void showCarInfo(){
		User user = connected();
		CarInformation carInfo = CarInformation.findByUser(user);
		if(carInfo==null){
			carInfo=new CarInformation(0, "", connected());
		}
		AppModel unis=new AppModel();
		render(carInfo, unis);
	}

}
