package com.example.moregrowth.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moregrowth.dto.PredictionRequest;
import com.example.moregrowth.dto.PredictionResponse;
import com.example.moregrowth.model.Enquiry;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PredictionService {
    
    private RestTemplate restTemplate = new RestTemplate();

    public PredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PredictionResponse callPythonModel(PredictionRequest request) {
        String pythonModelEndpoint = "http://localhost:5000/predict";
        return restTemplate.postForObject(pythonModelEndpoint, request, PredictionResponse.class);
    }


    public String runPythonScript(PredictionRequest request) {
        try {
            //connect and get users' data
            Enquiry enquiry = new Enquiry(
                request.getText(),
                request.getContactMethod(),
                request.getCustomerIdentity(),
                request.getCustomerAge(),
                request.getAreaOfResidence(),
                request.getIncomeLevel(),
                request.getExpenditureSituation(),
                request.getEmploymentStatus(),
                request.getPropertyOwnershipStatus(),
                request.getInvestmentIntent(),
                request.getPreviousTransactionExperience(),
                request.getFamilySituation(),
                request.getFinancialReadiness(),
                request.getPreferredCommunicationMethod(),
                request.getDecisionTimeframe(),
                request.getCreditScoreRange(),
                request.getInquiryLength(),
                request.getUrgencyIndicator(),
                request.getSentimentScore(),
                request.getKeywordsPresence(),
                request.getDayOfTheWeek(),
                request.getTimeOfDay(),
                request.getGeographicalIndicator(),
                request.getPreviousInteractions(),
                request.getInquirySource(),
                request.getPropertyTypeInterest(),
                request.getBudgetMentioned(),
                request.getName(),
                request.getPhoneNumber(),
                request.getStatus()
            );

            enquiry.display(enquiry);
            // transform the data to json
            ObjectMapper objectMapper = new ObjectMapper();
            String enquiryJson = objectMapper.writeValueAsString(enquiry);

            // write the data into temp file
            Path tempFile = Files.createTempFile("enquiry", ".json");
            Files.write(tempFile, enquiryJson.getBytes(StandardCharsets.UTF_8));
            //enquiry.display(enquiry);

            // print info for testing 
            System.out.println("Temporary file for JSON created at: " + tempFile);
            System.out.println("JSON being sent to Python: " + enquiryJson); 

            // use python script and set the file path
            String pythonScriptPath = "E:\\UTS\\31116 software development studio\\project\\Local connection without http.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, tempFile.toString());
            // combine the error info and output infor
            processBuilder.redirectErrorStream(true); 
            Process process = processBuilder.start();
            System.out.println("python start");

            //handle the errors and print the error information
            /*System.out.println("python end1");
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder();
            StringBuilder errorOutput = new StringBuilder();
            System.out.println("python end2");*/

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            System.out.println("python end2");

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            System.out.println("python end3");

            /* testing code
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error in Python script execution: " + errorOutput); // 打印错误信息
                return "Error executing script";
            }*/

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error in Python script execution: " + output); // 输出包含错误信息
                return "Error executing script";
            }
            System.out.println("python end4");

            // delete the tempfile
            Files.deleteIfExists(tempFile);

            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
}