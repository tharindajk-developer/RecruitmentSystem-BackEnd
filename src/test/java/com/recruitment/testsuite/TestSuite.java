package com.recruitment.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.recruitment.backend.CVTest;
import com.recruitment.backend.JobSeekerRecruiterTest;
import com.recruitment.backend.JobTest;
import com.recruitment.backend.JwtAuthenticationTest;
import com.recruitment.backend.RoleTest;
import com.recruitment.backend.UserTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   UserTest.class,
   CVTest.class,
   RoleTest.class,
   JobTest.class,
   JobSeekerRecruiterTest.class,
   JwtAuthenticationTest.class
})

public class TestSuite {   
}  