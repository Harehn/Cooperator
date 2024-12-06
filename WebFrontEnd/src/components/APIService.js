//Set API_URL to either deployed backend or localhost backend depending on vue.
var API_URL = 'https://cooperator-backend-9.herokuapp.com';
if (process.env.NODE_ENV == 'development') {
  API_URL = 'http://localhost:8000';
  console.log("changed API_URL to localhost for development!");
}

//axios API
import axios from 'axios';
axios.defaults.withCredentials = true;
axios.defaults.headers.common['Access-Control-Allow-Credentials'] = true;
axios.defaults.headers.common['Access-Control-Allow-Origin'] = API_URL;

//Axios debugger
require('axios-debug')(axios);

//The methods are listed in the following order of main concern:
// 1. EmployeeProfiles
// 2. EmploymentContracts
// 3. TaxForms
// 4. StudentEvaluationForms
// 5. Invitations and their SocialEvents
// 6. LoginCredentials
export class APIService {

  constructor() { }

  //EMPLOYEEPROFILE SECTION (1 of 6)
  //Returns an array of currently-active employees by the specified employer
  getCurrentEmployees(company, lastName, firstName) {
    const url = `${API_URL}/employer/${company}/${lastName}/${firstName}/employeeprofiles/current`;
    return axios.get(url).then(response => response.data);
  }
  //Returns an array of employee profiles who need their student evaluation forms within the defined time interval
  getEmployeesWithUpcomingEvaluations(company, lastName, firstName, deadlineInterval) {
    const url = `${API_URL}/employer/${company}/${lastName}/${firstName}/employeeprofiles/upcoming?numberOfDaysAtMostTodeadlines=${deadlineInterval}`;
    return axios.get(url).then(response => response.data);
  }
  //Returns an array of all the employees ever employed by the specified employer
  getAllEmployees(company, lastName, firstName) {
    const url = `${API_URL}/employeeprofile/${company}/${lastName}/${firstName}`;
    return axios.get(url).then(response => response.data);
  }

  //EMPLOYMENTCONTRACT SECTION (2 of 6)
  //Loads the byte array of the contract as a pdf-file file-stream. Could be used for live previews.
  //Note that the URI to this is actually included in the document's descriptors json.
  getEmploymentContract
    (company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/contract/${company}/${employerLastName}/${employerFirstName}/${studentId}/${startYear}/${startMonth}/${startDay}`;
    return axios.get(url).then(response => response.data);
  }
  //Returns an object describing the employment contract
  getEmploymentContractDescriptors
    (company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/contract/${company}/${employerLastName}/${employerFirstName}/${studentId}/${startYear}/${startMonth}/${startDay}/preview`;
    return axios.get(url).then(response => response.data);
  }
  //Confirms the defined contract
  confirmEmploymentContract
    (username, password, company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/employmentcontract/confirm/${studentId}/${company}/${employerFirstName}/${employerLastName}/${startYear}/${startMonth}/${startDay}?username=${username}&password=${password}`;
    return axios.put(url).then(response => response.data);
  };
  //denies the defined contract
  denyEmploymentContract
    (username, password, company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/employmentcontract/deny/${studentId}/${company}/${employerFirstName}/${employerLastName}/${startYear}/${startMonth}/${startDay}?username=${username}&password=${password}`;
    return axios.put(url).then(response => response.data);
  }

  //TAXFORM SECTION (3 of 6)
  //Streams taxform pdf data. Used for downloading, normally; potential for preview window like on my courses
  //Note that the URI to this is actually included in the document's descriptors json.
  getTaxForm(year, name) {
    const url = `${API_URL}/taxform/${year}/${name}`
    return axios.get(url).then(response => response.data);
  }
  //Returns the description data of the taxform
  getTaxFormDescriptors(year, name) {
    const url = `${API_URL}/taxform/${year}/${name}/preview`
    return axios.get(url).then(response => response.data);
  }
  //Returns a list of descriptors for the taxforms of the specified year
  getTaxFormDescriptorsOfYear(year) {
    const url = `${API_URL}/taxform/${year}/preview`
    return axios.get(url).then(response => response.data);
  }
  //Returns a list of descriptors for all of the taxforms in the database
  getTaxFormDescriptorsOfAll() {
    const url = `${API_URL}/taxform/previewall`
    return axios.get(url).then(response => response.data);
  }

  //STUDENTEVALUATIONFORM SECTION (4 of 6)
  //Streams out the bytes of the student evaluation form.
  //May be useful for giving a live preview of the pdf on page.
  //Note that the URI to this is actually included in the document's descriptors json.
  getStudentEvaluationForm
    (company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/studentevaluationform/${company}/${employerLastName}/${employerFirstName}/${studentId}/${startYear}/${startMonth}/${startDay}`;
    return axios.get(url).then(response => response.data);
  }
  //Returns an object describing the student evaluation form.
  getStudentEvaluationFormDescriptors
    (company, employerLastName, employerFirstName, studentId, startYear, startMonth, startDay) {
    const url = `${API_URL}/studentevaluationform/${company}/${employerLastName}/${employerFirstName}/${studentId}/${startYear}/${startMonth}/${startDay}/preview`;
    return axios.get(url).then(response => response.data);
  }
  //Streams out the bytes of the to-be-filled, empty student evaluation form
  //May be useful for giving al ive preview of the pdf on page.
  getBlankStudentEvaluationForm() {
    const url = `${API_URL}/studentevaluationform/empty`;
    return axios.get(url).then(response => response.data);
  }
  //Provide information about the student evaluation form in a json of desciprtors
  getBlankStudentEvaluationFormDescriptors() {
    const url = `${API_URL}/studentevaluationform/empty/preview`;
    return axios.get(url).then(response => response.data);
  }

  //INVITATIONS(5 of 6)
  //Returns information about the invitations received by the employer, including info of the social event.
  getInvitations(company,lastName,firstName){
    const url = `${API_URL}/employer/${company}/${lastName}/${firstName}/invitations`;
    return axios.get(url).then(response=>response.data);
  }
  //TODO: Writing methods for confirming or refusing the invitation. This requires a change in the backend, as
  //there is no put method for this. I will, however, write the method for doing this anyways, though:
  acceptInvitation(invitationId,username,password){
    const url = `${API_URL}/employer/invitation/${invitationId}/accept?username=${username}&password=${password}`;
    return axios.put(url).then(response=>response.data);
  }
  refuseInvitation(invitationId,username,password){
    const url = `${API_URL}/employer/invitation/${invitationId}/refuse?username=${username}&password=${password}`;
    return axios.put(url).then(response=>response.data);
  }

  //LOGINCREDENTIALS SECTION (6 of 6)
  //This is supposed to generate a session id for the employer as authentication (that's what this returns)
  login(username, password) {
    const url = `${API_URL}/login/${username}?password=${password}`;
    return axios.get(url).then(response => response.data);
  }
  //This checks to see if the provied username exists.
  checkUsername(username) {
    const url = `${API_URL}/login/${username}/checkexists`;
    return axios.get(url).then(response => response.data);
  }
  //This changes the password for the employer
  changePassword(username, oldPassword, newPassword1, newPassword2) {
    const url = `${API_URL}/login/changepassword?username=${username}&oldPassword=${oldPassword}&newPassword1=${newPassword1}&newPassword2=${newPassword2}`;
    return axios.put(url).then(response => response.data);
  }

}
