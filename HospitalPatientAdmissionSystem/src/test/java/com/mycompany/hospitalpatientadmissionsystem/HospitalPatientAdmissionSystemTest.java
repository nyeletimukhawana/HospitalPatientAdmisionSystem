/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.hospitalpatientadmissionsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class HospitalPatientAdmissionSystemTest {
 
    @BeforeEach
    public void setUp() {
        HospitalPatientAdmissionSystem.patients.clear();
 
        int bedCount = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                if (bedCount < 10) {
                    HospitalPatientAdmissionSystem.bedNumber[row][col] = "B0" + bedCount;
                } else {
                    HospitalPatientAdmissionSystem.bedNumber[row][col] = "B" + bedCount;
                }
                HospitalPatientAdmissionSystem.bedOccupant[row][col] = "";
                bedCount++;
            }
        }
    }
    @Test
    public void testRegisterPatient() {
        Patient p = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        boolean added = HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        assertTrue(added);
        assertEquals(1, HospitalPatientAdmissionSystem.patients.size());
    }
 
    @Test
    public void testPreventDuplicatePatientID() {
        Patient p1 = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P001", "Jane", "Doe", 25, "Female", "Cold", PatientCategory.EMERGENCY);
 
        HospitalPatientAdmissionSystem.registerPatientLogic(p1);
        boolean secondAdded = HospitalPatientAdmissionSystem.registerPatientLogic(p2);
 
        assertFalse(secondAdded);
        assertEquals(1, HospitalPatientAdmissionSystem.patients.size());
    }
    @Test
    public void testSearchPatientFound() {
        Patient p = new Patient("P002", "Sarah", "Jones", 40, "Female", "Diabetes", PatientCategory.OUTPATIENT);
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        Patient found = HospitalPatientAdmissionSystem.findPatientLogic("P002");
 
        assertNotNull(found);
        assertEquals("Sarah", found.getFirstName());
    }
 
    @Test
    public void testSearchPatientNotFound() {
        Patient found = HospitalPatientAdmissionSystem.findPatientLogic("DOES_NOT_EXIST");
        assertNull(found);
    }
    @Test
    public void testUpdatePatientDetails() {
        Patient p = new Patient("P003", "Tom", "Brown", 50, "Male", "Asthma", PatientCategory.OUTPATIENT);
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        HospitalPatientAdmissionSystem.updatePatientLogic(p, "Thomas", "Brown", 51, "Male", "Severe Asthma");
 
        assertEquals("Thomas", p.getFirstName());
        assertEquals(51, p.getAge());
        assertEquals("Severe Asthma", p.getMedicalCondition());
    }
    @Test
    public void testDeletePatient() {
        Patient p = new Patient("P004", "Amy", "White", 22, "Female", "Migraine", PatientCategory.EMERGENCY);
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        boolean deleted = HospitalPatientAdmissionSystem.deletePatientLogic("P004");
 
        assertTrue(deleted);
        assertEquals(0, HospitalPatientAdmissionSystem.patients.size());
    }
 
    @Test
    public void testDeletePatientNotFound() {
        boolean deleted = HospitalPatientAdmissionSystem.deletePatientLogic("DOES_NOT_EXIST");
        assertFalse(deleted);
    }
    @Test
    public void testAllocateBed() {
        Patient p = new Inpatient("P005", "Ben", "Green", 35, "Male", "Fracture");
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        String bed = HospitalPatientAdmissionSystem.allocateBedLogic("P005");
 
        assertNotNull(bed);
        assertEquals("B01", bed); 
    }
 
    @Test
    public void testPreventDuplicateAllocation() {
        Patient p = new Inpatient("P006", "Cara", "Lee", 45, "Female", "Surgery");
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
 
        HospitalPatientAdmissionSystem.allocateBedLogic("P006"); 
        String secondAttempt = HospitalPatientAdmissionSystem.allocateBedLogic("P006"); 
 
        assertNull(secondAttempt);
    }
 
    @Test
    public void testPreventAllocationWhenWardFull() {
        for (int i = 1; i <= 20; i++) {
            Patient p = new Inpatient("F" + i, "First" + i, "Last" + i, 30, "Male", "Condition");
            HospitalPatientAdmissionSystem.registerPatientLogic(p);
            HospitalPatientAdmissionSystem.allocateBedLogic("F" + i);
        }
 
        Patient extra = new Inpatient("F21", "Extra", "Patient", 30, "Male", "Condition");
        HospitalPatientAdmissionSystem.registerPatientLogic(extra);
        String result = HospitalPatientAdmissionSystem.allocateBedLogic("F21");
 
        assertNull(result);
    }
    @Test
    public void testReleaseBed() {
        Patient p = new Inpatient("P007", "Dan", "Ford", 28, "Male", "Appendicitis");
        HospitalPatientAdmissionSystem.registerPatientLogic(p);
        HospitalPatientAdmissionSystem.allocateBedLogic("P007");
 
        boolean released = HospitalPatientAdmissionSystem.releaseBedLogic("B01");
 
        assertTrue(released);
        assertEquals("", HospitalPatientAdmissionSystem.bedOccupant[0][0]);
    }
 
    @Test
    public void testReleaseBedAlreadyEmpty() {
        boolean released = HospitalPatientAdmissionSystem.releaseBedLogic("B01");
        assertFalse(released);
    }
 
    @Test
    public void testSortPatientsByID() {
        HospitalPatientAdmissionSystem.registerPatientLogic(
                new Patient("P003", "C", "C", 20, "Male", "Cond", PatientCategory.OUTPATIENT));
        HospitalPatientAdmissionSystem.registerPatientLogic(
                new Patient("P001", "A", "A", 20, "Male", "Cond", PatientCategory.OUTPATIENT));
        HospitalPatientAdmissionSystem.registerPatientLogic(
                new Patient("P002", "B", "B", 20, "Male", "Cond", PatientCategory.OUTPATIENT));
 
        HospitalPatientAdmissionSystem.sortPatientsByID();
 
        assertEquals("P001", HospitalPatientAdmissionSystem.patients.get(0).getPatientID());
        assertEquals("P002", HospitalPatientAdmissionSystem.patients.get(1).getPatientID());
        assertEquals("P003", HospitalPatientAdmissionSystem.patients.get(2).getPatientID());
    }
}