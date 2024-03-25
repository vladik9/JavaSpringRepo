package com.example.app;

import com.example.app.Car.Car;
import com.example.app.Car.CarRepository;
import com.example.app.CarVIN.CarVIN;
import com.example.app.CarVIN.CarVINRepository;
import com.example.app.Driver.DriverRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;

@SpringBootApplication
public class AppApplication {
	

	public static void main (String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	// this will run after app starts and will run a cmd line runner that will
	// create a user and add it in DB, need to mark as bea as this will be used by
	// Hibernate to identify it
	@Bean
	CommandLineRunner commandLineRunner(CarRepository carRepository, InputReader inputInputReader , CarVINRepository carVINRepository, DriverRepository driverRepository) {
		return args -> {
			int option=0;
			do {
				System.out.println("\nChoose a option your option:");
				System.out.println("From Valid options:");
				System.out.println("Option 1: Create and save a new car");
				System.out.println("Option 2: Get a car by Id");
				System.out.println("Option 0: Exit");
				option = Integer.parseInt(inputInputReader.readInput());
				System.out.println("You choose: " + option);
				
			switch (option) {
					case 0:
						System.exit(0);
					break;
					case 1: {
						try {
							carRepository.save(readInputCar(inputInputReader));
						} catch (DataAccessException ex) {
							System.out.println(ex.getCause().getMessage());
						}
						break;
					}
					case 2: {
						try {
							Long id = Long.parseLong(inputInputReader.readInput("Enter your id:"));
							carRepository.findById(id)
									.
							ifPresentOrElse((c -> System.out.println(c)),
							()->{System.out.println("Not found");});
						} catch (DataAccessException ex) {
							System.out.println(ex.getCause().getMessage());
						}
						break;
					}
					default:
					{
						System.out.println(option+ " is not a valid option");
					}
					break;
	}
			} while (option != 0);
		};

	}
	   // Assuming InputReader is in the same package or is component scanned
		 @Bean
			InputReader inputReader() {
				return new InputReader();
			}

			private static Car readInputCar(InputReader inputInputReader) {
				String name, color;
				Integer year, number_of_seats;
				name = inputInputReader.readInput("Enter car name: ");
				color = inputInputReader.readInput("Enter car color:");
				year = Integer.parseInt(inputInputReader.readInput("Enter car year: "));
				number_of_seats = Integer.parseInt(inputInputReader.readInput("Enter car number of seats: "));
				Faker faker = new Faker();
				String VIN = faker.bothify("?????################").toUpperCase();
				CarVIN carVIN = new CarVIN(VIN);
				return new Car(name, color, year, number_of_seats, carVIN );
			}

			private static CarVIN readInputCarVin(InputReader inputReader) {
				Faker faker = new Faker();
				String VIN = faker.bothify("?????################").toUpperCase();
        System.out.println("Generated VIN: " + VIN);
				return new CarVIN( VIN);
			}

}
