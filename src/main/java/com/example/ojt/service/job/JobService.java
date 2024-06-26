package com.example.ojt.service.job;

import com.example.ojt.config.LocalDateFormatter;
import com.example.ojt.config.StringToInteger;
import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.JobRequestDTO;
import com.example.ojt.model.dto.request.LeveJobRequestDTO;
import com.example.ojt.model.dto.request.TypeJobRequestDTO;
import com.example.ojt.model.entity.*;
import com.example.ojt.repository.*;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JobService implements IJobService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private ITypeJobRepository typeJobRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ITypesJobsRepository typesJobsRepository;
    @Autowired
    private ILeveJobRepository leveJobRepository;
    @Autowired
    private ILevelsJobsRepository levelsJobsRepository;

    @Override
    @Transactional
    public boolean save(JobRequestDTO jobRequestDTO) throws CustomException {
        // Get authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            // Find the account by ID
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            // Helper to convert String to Integer
            StringToInteger stringToInteger = new StringToInteger();

            // Find the address by ID
            AddressCompany addressCompany = addressRepository.findById(jobRequestDTO.getAddressId())
                    .orElseThrow(() -> new CustomException("Address is not found with id " + jobRequestDTO.getAddressId(), HttpStatus.NOT_FOUND));

            // Create a new Job entity
            Job job = Job.builder()
                    .company(account.getCompany())
                    .addressCompany(addressCompany)
                    .description(jobRequestDTO.getDescription())
                    .requirements(jobRequestDTO.getRequirements())
                    .title(jobRequestDTO.getTitle())
                    .expireAt(jobRequestDTO.getExpireAt())
                    .salaryFrom(stringToInteger.parse(jobRequestDTO.getSalaryFrom()))
                    .salaryTo(stringToInteger.parse(jobRequestDTO.getSalaryTo()))
                    .createdAt(new Date())
                    .status(1)
                    .build();

            // Save the job first to generate its ID
            job = jobRepository.save(job);

            // Handle TypesJobs association
            Set<TypesJobs> typesJobsSet = new HashSet<>();
            if (jobRequestDTO.getTypeJobSet() != null) {
                for (TypeJobRequestDTO typeJobRequestDTO : jobRequestDTO.getTypeJobSet()) {
                    Optional<TypeJob> optionalTypeJob = typeJobRepository.findByName(typeJobRequestDTO.getName());
                    TypeJob typeJob;
                    if (optionalTypeJob.isPresent()) {
                        typeJob = optionalTypeJob.get();
                    } else {
                        typeJob = TypeJob.builder().name(typeJobRequestDTO.getName()).build();
                        typeJob = typeJobRepository.save(typeJob);
                    }

                    TypesJobs typesJobs = TypesJobs.builder()
                            .job(job)
                            .typeJob(typeJob)
                            .build();
                    typesJobsSet.add(typesJobs);
                }
            }

            // Handle LevelsJobs association
            Set<LevelsJobs> levelsJobsSet = new HashSet<>();
            if (jobRequestDTO.getLevelJob() != null) {
                for (LeveJobRequestDTO leveJobRequestDTO : jobRequestDTO.getLevelJob()) {
                    Optional<LevelJob> optionalLevelJob = leveJobRepository.findByName(leveJobRequestDTO.getLeveJobName());
                    LevelJob levelJob;
                    if (optionalLevelJob.isPresent()) {
                        levelJob = optionalLevelJob.get();
                    } else {
                        levelJob = LevelJob.builder().name(leveJobRequestDTO.getLeveJobName()).build();
                        levelJob = leveJobRepository.save(levelJob);
                    }

                    LevelsJobs levelsJobs = LevelsJobs.builder()
                            .job(job)
                            .levelJob(levelJob)
                            .build();
                    levelsJobsSet.add(levelsJobs);
                }
            }

            // Save typesJobsSet after the job has been saved
            typesJobsRepository.saveAll(typesJobsSet);
            levelsJobsRepository.saveAll(levelsJobsSet);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean update(JobRequestDTO jobRequestDTO, Integer jobId) throws CustomException {
        // Get authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            // Find the account by ID
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            // Find the job by ID
            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new CustomException("Job is not found with id " + jobId, HttpStatus.NOT_FOUND));

            // Helper classes for date and string to integer conversion
            LocalDateFormatter localDateFormatter = new LocalDateFormatter();
            StringToInteger stringToInteger = new StringToInteger();

            // Find the address by ID
            AddressCompany addressCompany = addressRepository.findById(jobRequestDTO.getAddressId())
                    .orElseThrow(() -> new CustomException("Address is not found with id " + jobRequestDTO.getAddressId(), HttpStatus.NOT_FOUND));

            // Update job fields
            job.setCompany(account.getCompany());
            job.setAddressCompany(addressCompany);
            job.setDescription(jobRequestDTO.getDescription());
            job.setRequirements(jobRequestDTO.getRequirements());
            job.setTitle(jobRequestDTO.getTitle());
            job.setExpireAt(jobRequestDTO.getExpireAt());
            job.setSalaryFrom(stringToInteger.parse(jobRequestDTO.getSalaryFrom()));
            job.setSalaryTo(stringToInteger.parse(jobRequestDTO.getSalaryTo()));
            job.setCreatedAt(new Timestamp(new Date().getTime()));

            // Update typesJobsSet
            Set<TypesJobs> typesJobsSet = new HashSet<>();
            if (jobRequestDTO.getTypeJobSet() != null) {
                for (TypeJobRequestDTO typeJobRequestDTO : jobRequestDTO.getTypeJobSet()) {
                    Optional<TypeJob> optionalTypeJob = typeJobRepository.findByName(typeJobRequestDTO.getName());
                    TypeJob typeJob;
                    if (optionalTypeJob.isPresent()) {
                        typeJob = optionalTypeJob.get();
                    } else {
                        typeJob = TypeJob.builder().name(typeJobRequestDTO.getName()).build();
                        typeJob = typeJobRepository.save(typeJob);
                    }

                    TypesJobs typesJobs = TypesJobs.builder()
                            .job(job)
                            .typeJob(typeJob)
                            .build();
                    typesJobsSet.add(typesJobs);
                }
            }

            // Clear old typesJobsSet and save new one
            typesJobsRepository.deleteAllByJob(job);
            typesJobsRepository.saveAll(typesJobsSet);

            // Update levelsJobsSet
            Set<LevelsJobs> levelsJobsSet = new HashSet<>();
            if (jobRequestDTO.getLevelJob() != null) {
                for (LeveJobRequestDTO leveJobRequestDTO : jobRequestDTO.getLevelJob()) {
                    Optional<LevelJob> optionalLevelJob = leveJobRepository.findByName(leveJobRequestDTO.getLeveJobName());
                    LevelJob levelJob;
                    if (optionalLevelJob.isPresent()) {
                        levelJob = optionalLevelJob.get();
                    } else {
                        levelJob = LevelJob.builder().name(leveJobRequestDTO.getLeveJobName()).build();
                        levelJob = leveJobRepository.save(levelJob);
                    }

                    LevelsJobs levelsJobs = LevelsJobs.builder()
                            .job(job)
                            .levelJob(levelJob)
                            .build();
                    levelsJobsSet.add(levelsJobs);
                }
            }

            // Clear old levelsJobsSet and save new one
            levelsJobsRepository.deleteAllByJob(job);
            levelsJobsRepository.saveAll(levelsJobsSet);

            // Save the updated job
            jobRepository.save(job);
            return true;
        }
        return false;
    }

}
