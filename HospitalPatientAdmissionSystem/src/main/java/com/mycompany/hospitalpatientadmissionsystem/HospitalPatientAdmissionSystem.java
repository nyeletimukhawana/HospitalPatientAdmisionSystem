/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalpatientadmissionsystem;

/**
 *
 * @author PC
 */
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalPatientAdmissionSystem {
    
    //ArrayList grows and shrinks dynamically that's why it's most suitable to use them here.
    //This ArrayList store all registered patient object
    static ArrayList<Patient> patients = new ArrayList<>();
    
    //The ward bed layout
    static String[][] bedNumber = new String[4][5];
    static String[][] bedOccupant = new String[4][5];
    
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        int bedCount = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedCount < 10) {
                    bedNumber[row][col] = "B0" + bedCount;
                } else {
                    bedNumber[row][col] = "B" + bedCount;
                }
                bedOccupant[row][col] = "";
                bedCount++;
            }
        }
        int option = 0;
        
        while (option != 12) {
            System.out.println("\n MediCare PATIENT ADMISSION SYSTEM.");
            System.out.println("1. Register a new patient");
            System.out.println("2. Search for a patient with patient ID");
            System.out.println("3. Update an existing patient's details");
            System.out.println("4. Delete a patient");
            System.out.println("5. Display all registered patients");
            System.out.println("6. Allocate an available to an inpatient");
            System.out.println("7. Release a bed");
            System.out.println("8. Display ward layout");
            System.out.println("9. Display available beds");
            System.out.println("10.Display occupied beds");
            System.out.println("11.Display ward report");
            System.out.println("12.EXIT");

            System.out.print("Choose option: ");
            option = Integer.parseInt(input.nextLine());
            
            switch (option) {
                case 1:
                    registerPatient();
                    break;
 
                case 2:
                    searchPatient();
                    break;
 
                case 3:
                    updatePatientDetails();
                    break;
 
                case 4:
                    deletePatient();
                    break;
 
                case 5:
                    displayAllPatients();
                    break;
                    
                case 6:
                    allocateBed();
                    break;
                    
                case 7:
                    releaseBed();
                    break;
                    
                case 8:
                    displayWardLayout();
                    break;
                    
                case 9:
                    displayAvailableBeds();
                    break;
                    
                case 10:
                    displayOccupiedBeds();
                    break;
 
                case 11:
                    generateWardReport();
                    break;
                    
                case 12:
                    System.out.println("Exiting the system");
                    break;
 
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
//PATIENT MANAGEMENT
private static void registerPatient() {
        System.out.println("--- Register a New Patient ---");

    System.out.print("Enter Patient ID: ");
    String id = input.nextLine();

    System.out.print("Enter First Name: ");
    String first = input.nextLine();

    System.out.print("Enter Last Name: ");
    String last = input.nextLine();

    System.out.print("Enter Age: ");
    int patientAge = input.nextInt();
    input.nextLine();

    System.out.print("Enter Gender: ");
    String patientGender = input.nextLine();

    System.out.print("Enter Medical Condition: ");
    String condition = input.nextLine();

    System.out.println("\nSelect Patient Category:");
    System.out.println("1. Inpatient");
    System.out.println("2. Outpatient");
    System.out.println("3. Emergency");
    System.out.print("Enter your choice: ");

    int categoryChoice = input.nextInt();
    input.nextLine();

    Patient newPatient;

    if (categoryChoice == 1) {
        newPatient = new Inpatient(id, first, last, patientAge, patientGender, condition);
    } else if (categoryChoice == 2) {
        newPatient = new Patient(id, first, last, patientAge, patientGender, condition, PatientCategory.OUTPATIENT);
    } else if (categoryChoice == 3) {
        newPatient = new Patient(id, first, last, patientAge, patientGender, condition, PatientCategory.EMERGENCY);
    } else {
        System.out.println("Invalid patient category.");
        return;
    }
    boolean added = registerPatientLogic(newPatient);
 
        if (added) {
            System.out.println("Patient " + first + " " + last + " successfully registered with ID " + id);
        } else {
            System.out.println("A patient with ID " + id + " already exists.");
        }
    }

 // The Logic adds a patient if the ID isn't already taken and returns true if added, false if it's a duplicate.
    static boolean registerPatientLogic(Patient newPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientID().equals(newPatient.getPatientID())) {
                return false;
            }
        }
        patients.add(newPatient);
        return true;
    }
 
    private static void searchPatient() {
        System.out.println("\n--- Search for a Patient ---");
 
        System.out.print("Enter Patient ID: ");
        String searchID = input.nextLine();
 
        Patient found = findPatientLogic(searchID);
 
        if (found != null) {
            System.out.println("\nPatient Found");
            found.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }
 
    // The Logic returns the patient with this ID, or null if none exists
    static Patient findPatientLogic(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientID().equals(id)) {
                return patients.get(i);
            }
        }
        return null;
    }
 
    private static void updatePatientDetails() {
        System.out.println("---Update Patient Details ---");
 
        System.out.print("Enter Patient ID: ");
        String searchID = input.nextLine();
 
        Patient patient = findPatientLogic(searchID);
 
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
 
        System.out.print("Enter First Name: ");
        String first = input.nextLine();
 
        System.out.print("Enter Last Name: ");
        String last = input.nextLine();
 
        System.out.print("Enter Age: ");
        int newAge = input.nextInt();
        input.nextLine();
 
        System.out.print("Enter Gender: ");
        String newGender = input.nextLine();
 
        System.out.print("Enter Medical Condition: ");
        String newCondition = input.nextLine();
 
        updatePatientLogic(patient, first, last, newAge, newGender, newCondition);
 
        System.out.println("Patient details updated successfully.");
    }
 
    static void updatePatientLogic(Patient patient, String firstName, String lastName,
                                    int age, String gender, String medicalCondition) {
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
    }
 
    private static void deletePatient() {
        System.out.println("---Delete a Patient");
 
        System.out.print("Enter Patient ID: ");
        String searchID = input.nextLine();
 
        boolean deleted = deletePatientLogic(searchID);
 
        if (deleted) {
            System.out.println("Patient has been successfully deleted.");
        } else {
            System.out.println("Patient not found.");
        }
    }
    // The Logic removes the patient with this ID and returns true if a patient was removed or false if not found.
    static boolean deletePatientLogic(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientID().equals(id)) {
                patients.remove(i);
                return true;
            }
        }
        return false;
    }
 
    private static void displayAllPatients() {
        System.out.println("\n---All Registered Patients---");
 
        if (patients.isEmpty()) {
            System.out.println("No patients have been registered.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            System.out.println("\nPatient " + (i + 1));
            patients.get(i).displayDetails();
        }
    }
 
    // BED MANAGEMENT
    private static void allocateBed() {
        System.out.println("\n---Allocate an Available Bed To an Inpatient---");
 
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
 
        String result = allocateBedLogic(id);
 
        if (result == null) {
            System.out.println("Bed could not be allocated (patient not found, not an Inpatient, already has a bed, or no beds available).");
        } else {
            System.out.println("Bed " + result + " allocated to patient " + id);
        }
    }
 
    // The Logic tries to allocate the available bed to the given patient.
    static String allocateBedLogic(String id) {
        Patient foundPatient = findPatientLogic(id);
 
        if (foundPatient == null) {
            return null;
        }
 
        if (!(foundPatient instanceof Inpatient)) {
            return null;
        }
 
        Inpatient inpatient = (Inpatient) foundPatient;
 
        if (!inpatient.getBedNumber().equals("")) {
            return null; //To avoid invalid and prevent duplicate allocation
        }
 
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedOccupant[row][col].equals("")) {
                    bedOccupant[row][col] = id;
                    inpatient.setWardNumber("Ward 1");
                    inpatient.setBedNumber(bedNumber[row][col]);
                    return bedNumber[row][col];
                }
            }
        }
 
        return null; 
    }
 
    private static void releaseBed() {
        System.out.println("---\nRelease A Bed---");
 
        System.out.print("Enter bed number: ");
        String bed = input.nextLine();
 
        boolean released = releaseBedLogic(bed);
 
        if (released) {
            System.out.println("Bed " + bed + " released.");
        } else {
            System.out.println("Bed number not found, or that bed is already empty.");
        }
    }
 
    // The Logic releases the given bed if it exists and is occupied
    static boolean releaseBedLogic(String bed) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedNumber[row][col].equals(bed)) {
 
                    if (bedOccupant[row][col].equals("")) {
                        return false;
                    }
 
                    String occupantID = bedOccupant[row][col];
 
                    for (int i = 0; i < patients.size(); i++) {
                        if (patients.get(i).getPatientID().equals(occupantID) && patients.get(i) instanceof Inpatient) {
                            Inpatient inpatient = (Inpatient) patients.get(i);
                            inpatient.setWardNumber("");
                            inpatient.setBedNumber("");
                        }
                    }
 
                    bedOccupant[row][col] = "";
                    return true;
                }
            }
        }
        return false;
    }
 
    private static void displayWardLayout() {
        System.out.println("---\nComplete Ward Layout ---");
 
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedOccupant[row][col].equals("")) {
                    System.out.print(bedNumber[row][col] + "[Free]     ");
                } else {
                    System.out.print(bedNumber[row][col] + "[Occupied] ");
                }
            }
            System.out.println(); // move to the next line after printing all 5 beds in this row
        }
    }
 
    private static void displayAvailableBeds() {
        System.out.println("---\nAvailable Beds---");
 
        boolean found = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedOccupant[row][col].equals("")) {
                    System.out.println(bedNumber[row][col]);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No beds are available.");
        }
    }
 
    private static void displayOccupiedBeds() {
        System.out.println("---\nOccupied Beds---");
 
        boolean found = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (!bedOccupant[row][col].equals("")) {
                    System.out.println(bedNumber[row][col] + " Patient ID: " + bedOccupant[row][col]);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No beds are currently occupied.");
        }
    }
 
    // REPORTS
    private static void generateWardReport() {
        System.out.println("---\nWARD REPORTS---");
 
        sortPatientsByID();
        System.out.println("\n--- Patient Report ---");
        System.out.println("Total Registered Patients: " + patients.size());
 
        if (patients.isEmpty()) {
            System.out.println("No patients have been registered.");
        } else {
            System.out.println("\nAll Registered Patients:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.println("\nPatient " + (i + 1));
                patients.get(i).displayDetails();
            }
        }
        System.out.println("\n--- Bed Occupancy Report ---");
 
        int occupiedBeds = 0;
        int availableBeds = 0;
 
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedOccupant[row][col].equals("")) {
                    availableBeds++;
                } else {
                    occupiedBeds++;
                }
            }
        }
        double occupancyPercentage = (occupiedBeds / 20.0) * 100;
 
        System.out.println("Total Beds: 20");
        System.out.println("Occupied Beds: " + occupiedBeds);
        System.out.println("Available Beds: " + availableBeds);
        System.out.println("Ward Occupancy: " + occupancyPercentage + "%");
 
        System.out.println("\n--- Occupied Beds ---");
        if (occupiedBeds == 0) {
            System.out.println("No beds are currently occupied.");
        } else {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 5; col++) {
                    if (!bedOccupant[row][col].equals("")) {
                        System.out.println(bedNumber[row][col] + " Patient ID: " + bedOccupant[row][col]);
                    }
                }
            }
        }
 
        System.out.println("\n--- Available Beds ---");
        if (availableBeds == 0) {
            System.out.println("No beds are currently available.");
        } else {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 5; col++) {
                    if (bedOccupant[row][col].equals("")) {
                        System.out.println(bedNumber[row][col]);
                    }
                }
            }
        }
    }
    static void sortPatientsByID() {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).getPatientID().compareTo(patients.get(j).getPatientID()) > 0) {
                    Patient temp = patients.get(i);
                    patients.set(i, patients.get(j));
                    patients.set(j, temp);
                }
            }
        }
    }
}