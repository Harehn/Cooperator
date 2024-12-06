<template>
  <div id="employeeprofilemenu">
    <!-- <div class="edge_left edge_right bgColor1 paddedThick"> -->
    <div class="edge_sides bgColor1 paddedThick" style="text-align:center">
      <h1>Student Employees of {{propEmployer.company}}</h1>
    </div>
    <div class="twoTables">
      <div class="tableDiv">
        <table>
          <thead>
            <tr>
              <td colspan="3">
                <button class="filterButtons" v-on:click="queryState='All'">Filter by All Students</button>
              </td>
              <td style="background-color:#6e909d"></td>
              <td colspan="3">
                <button
                  class="filterButtons"
                  v-on:click="queryState='Current'"
                >Filter by Current Students</button>
              </td>
            </tr>
            <tr>
              <th></th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Status</th>
              <th>Attention Required</th>
              <th>Verification Status</th>
              <th></th>
            </tr>
            <tr v-if="employeeProfiles == null">
              <th
                align="center"
                colspan="7"
                style="background-color: #7f1818"
              >No Student Employees in {{employer.company}}</th>
            </tr>
          </thead>

          <tbody v-for="profile in employeeProfiles" v-bind:key="profile.id">
            <tr>
              <td class="tableButtons">
                <button
                  v-on:click="displayAdditionalInfo=true, employeeProfile=profile, convertToDate(0, employeeProfile.startDate), convertToDate(1, employeeProfile.endDate)"
                >More Details</button>
              </td>
              <td>{{profile.studentFirstName}}</td>
              <td>{{profile.studentLastName}}</td>
              <td>{{profile.status}}</td>
              <td
                v-if="profile.evaluationOfStudent!=null"
                style="background-color:#99e699;width=10px"
              >Eval Completed</td>
              <td v-else style="background-color:#ffe680">Eval Not Completed</td>
              <td>{{profile.contract.verified}}</td>
              <td class="tableButtons">
                <button
                  class="verifyButton"
                  v-on:click="verifyCoOpWindow=true, employeeProfile=profile, displayAdditionalInfo=false"
                >Verify or Deny CoOp</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="additionalInfo" v-if="displayAdditionalInfo == true">
        <table>
          <tr>
            <th>Student ID</th>
            <th>Start date</th>
            <th>End date</th>
            <th>Employment Contract</th>
            <th></th>
          </tr>
          <tr>
            <td>{{employeeProfile.studentId}}</td>
            <td>{{employeeProfile.startDate}}</td>
            <td>{{employeeProfile.endDate}}</td>
            <td>
              <button
                v-on:click="createAndOpenURI(employeeProfile.contract.uri)"
              >Employement Contract</button>
            </td>
            <td>
              <button v-on:click="displayAdditionalInfo=false">Close</button>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="bgColor1 verificationWindow" v-if="verifyCoOpWindow==true">
      <p>Please enter username and password to verify or deny CoOp for {{employeeProfile.studentFirstName}} {{employeeProfile.studentLastName}}</p>
      <div class="spacing">
        <input type="username" v-model="username" placeholder="Username">
      </div>
      <div class="spacing">
        <input type="password" v-model="password" placeholder="Password">
      </div>
      <button
        class="acceptCoOp"
        v-on:click="loginWindow = true, confirmCoOp(employeeProfile,verifyCoOpWindow=false)"
      >Accept CoOp</button>

      <button
        class="denyCoOp"
        v-on:click="denyCoOp(employeeProfile), loginWindow = true, verifyCoOpWindow=false"
      >Deny CoOp</button>

      <button v-on:click="verifyCoOpWindow=false">Cancel</button>

      <p v-if="this.fetching == true && this.loginWindow==true">Attempting to login...</p>
    </div>

    <div class="bgColor1 verificationWindow" v-if="this.loginWindow == true">
      <div v-if="this.correctLogin==false">
        <p>Login Credentials Incorrect</p>
        <button v-on:click="correctLogin=true, this.loginWindow=false">Okay</button>
      </div>
      <div v-if="this.correctLogin==true">
        <p>CoOp Status Updated: Refresh Page</p>
        <br>
        <button v-on:click="this.correctLogin=true, this.loginWindow=false, update()">Okay</button>
      </div>
    </div>
  </div>
</template>



<script>
import { APIService } from "./APIService"; //Backend-communication handler
const apiService = new APIService();

import { EventBus } from "./EventBus"; //Event handler

export default {
  name: "EmployeeProfileFrame",
  //Added prop to pass in employer
  props: {
    propEmployer: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      employee: {
        firstName: "",
        lastName: "",
        company: ""
      },
      employeeProfiles: [],
      employeeProfile: null,
      numberOfEmployeeProfiles: 0,
      taxForms: [],
      taxForm: null,
      numberOfTaxForms: 0,
      studentEvaluationForm: null,
      employmentContract: null,
      queryState: "All",
      displayAdditionalInfo: false,
      //Login info
      verifyCoOpWindow: false,
      username: "",
      password: "",
      correctLogin: true,
      loginWindow: false,
      fetching: false
    };
  },
  methods: {
    update() {
      if (this.queryState == "All") {
        this.getAllEmployees();
      }
      if (this.queryState == "Current") {
        this.getCurrentEmployees();
      }
    },
    //Creates URI and opens it in new tab
    createAndOpenURI(uri) {
      //Localhost for dev purposes
      var API_URL = "http://localhost:8000";
      var URI = `${API_URL}${uri}`;
      window.open(URI, "_Document");
    },
    //Converts from an array to Date
    convertToDate(toggle, dates) {
      var properDate = new Date(dates);
      if (properDate instanceof Date && !isNaN(properDate)) {
        if (toggle == 0) {
          this.employeeProfile.startDate = properDate;
        } else {
          this.employeeProfile.endDate = properDate;
        }
        return properDate;
      } else {
        var manualDate;
        var stringMonth;
        var year = dates[0];
        var month = dates[1];
        var day = dates[2];

        if (month == 1) {
          stringMonth = "January ";
        } else if (month == 2) {
          stringMonth = "February ";
        } else if (month == 3) {
          stringMonth = "March ";
        } else if (month == 4) {
          stringMonth = "April ";
        } else if (month == 5) {
          stringMonth = "May ";
        } else if (month == 6) {
          stringMonth = "June ";
        } else if (month == 7) {
          stringMonth = "July ";
        } else if (month == 8) {
          stringMonth = "August ";
        } else if (month == 9) {
          stringMonth = "September ";
        } else if (month == 10) {
          stringMonth = "October ";
        } else if (month == 11) {
          stringMonth = "November ";
        } else {
          stringMonth = "December ";
        }
        manualDate = stringMonth + day + " " + year;
        if (toggle == 0) {
          this.employeeProfile.startDate = properDate;
        } else {
          this.employeeProfile.endDate = properDate;
        }
        console.log(manualDate);
        return manualDate;
      }
    },
    confirmCoOp(profile) {
      this.fetching = true;
      console.log("Confirming CoOp");
      var allStartDates = profile.startDate;
      var startYear = allStartDates[0];
      var startMonth = allStartDates[1];
      var startDay = allStartDates[2];

      apiService
        .confirmEmploymentContract(
          this.username,
          this.password,
          this.propEmployer.company,
          this.propEmployer.lastName,
          this.propEmployer.firstName,
          profile.studentId,
          startYear,
          startMonth,
          startDay
        )
        .then(err => {
          this.correctLogin = false;
        })
        .catch(e => {
          this.correctLogin = false;
        });
      this.fetching = false;
    },
    denyCoOp(profile) {
      this.fetching = true;
      var allStartDates = profile.startDate;

      var startYear = allStartDates[0];
      var startMonth = allStartDates[1];
      var startDay = allStartDates[2];

      apiService
        .denyEmploymentContract(
          this.username,
          this.password,
          this.propEmployer.company,
          this.propEmployer.lastName,
          this.propEmployer.firstName,
          profile.studentId,
          startYear,
          startMonth,
          startDay
        )
        .then(err => {
          this.correctLogin = false;
        })
        .catch(e => {
          this.correctLogin = false;
        });
      this.fetching = false;
    },
    //Data collector for all employees for an employer
    getAllEmployees() {
      apiService
        .getAllEmployees(
          this.propEmployer.company,
          this.propEmployer.lastName,
          this.propEmployer.firstName
        )
        .then(data => {
          this.employeeProfiles = data;
          this.numberOfEmployeeProfiles = data.length;
          console.log("getAllEmployees called.");
        });
    },
    //Data collector for all current employees for an employer
    getCurrentEmployees() {
      apiService
        .getCurrentEmployees(
          this.employer.company,
          this.employer.lastName,
          this.employer.firstName
        )
        .then(data => {
          this.employeeProfiles = data;
          this.numberOfEmployeeProfiles = data.length;
          console.log("getCurrentEmployees called.");
        });
    }
  },
  mounted() {
    EventBus.$on("openingEmployeePage", employer => {
      this.update();
      console.log(
        "Received openingEmployeePage event. Setting company " +
          employer.company +
          " and updating."
      );
    });
  }
};
</script>



<style>
/* Multi-use */
.base {
  position: relative;
}
.edge_top {
  position: relative;
  top: 5px;
  padding-top: 25px;
}
.edge_sides {
  position: relative;
  left: 5px;
  right: 5px;
  padding-left: 25px;
  padding-right: 25px;
}
.edge_left {
  position: relative;
  left: 5px;
  padding-left: 25px;
}
.edge_right {
  position: relative;
  right: 5px;
  padding-right: 25px;
}
.edge_bottom {
  position: absolute;
  bottom: 5px;
  padding-bottom: 25px;
}
.edge_to_bottom {
  position: absolute;
  margin-top: 100vh;
}
.section_horizontal {
  width: 100%;
}
.vertically_aligned {
  align-items: centered;
  text-align: center;
}
.bgColor1 {
  background-color: #b1edff;
}
.bgColor2 {
  background-color: #2b323c;
}
/* .bgColor3 {
  background-color: #6e909d;
} */
.paddedThick {
  padding: 50px;
}
.paddedThin {
  padding: 10px;
}
.tableDiv {
  padding-left: 10px;
  position: relative;
  max-width: 50%;
  float: left;
}
.additionalInfo {
  max-width: 50%;
  position: relative;
  float: left;
  overflow: hidden;
}
.tableButtons {
  border: none;
}
.spacing {
  margin-bottom: 10px;
}
/* Specifics */
.verificationWindow {
  width: 30%;
  position: relative;
  float: left;
  padding: 10px;
  left: 50px;
  top: 11px;
}
.denyCoOp {
  background-color: #7f1818;
}
.denyCoOp:hover {
  background-color: red;
}

.acceptCoOp {
  background-color: #00802b;
}
.acceptCoOp:hover {
  background-color: #33cc33;
}

table {
  float: left;
  margin-top: 10px;
}

table,
th,
td {
  background-color: #b1edff;
  border: 1px solid black;
  border-collapse: collapse;
  padding: 5px;
  text-align: center;
}
th {
  background-color: #6e909d;
  color: white;
}
</style>
