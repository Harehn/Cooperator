// THIS FILE IS NOT MEANT FOR USE BY ANYONE.
// The contents of the file are the format in which all the objects from the database will be provided in.
// That is to say, the JSONs of the data receivable.
// Of course, a lot of times the JSONs come in an array of said JSONs. Read the comments in APIService to know
//    when you will get array outputs ("I call them lists informally") or actually run the HTTP commands and
//    see if the output comes wrapped in [], ie array brackets.

//the specialized JSONs that you be obtained fromt he database are:
// 1. EmployeeProfileDescriptors JSON
// 2. EmploymentContractDescriptors JSON
// 3. TaxFormsDescriptors JSON
// 4. StudentEvaluationFormDescriptors JSON
// 5. InvitationDescriptors JSON



//EmployeeProfileDescriptors JSON (you use these as employeeprofile's)
{
  //Basic attributes
  id:0,                   //numeric id of this profile on the DB
  studentId:0,            //Self explanatory(SE)
  studentFirstName:'',    //SE
  studentLastName:'',     //SE
  employerId:0,           //numeric id of this employer on the DB
  companyName:'',         //SE
  employerFirstName:'',   //SE
  employerLastName:'',    //SE
  startDate:[],           //Start date represented as an array of integers of length 3, in order [year, month, day]
  endDate:[],             //End date '' 
  status:'',              //Enum value which comes out as a string when in JSON form.
                          //  status can only be: 'INACTIVE', 'ACTIVE', 'ABANDONED', or 'DENIED'
  //Complex attributes (other JSONs)
  //employment contract json. This will never be null, and the form of contract is always as follows:
  contract:
  {
    docid: 0,           //numeric doc id on the DB for this doc
    name: '',           //Self Explanatory (SE)
    description: '',    //SE
    instructions: '',   //SE
    uri: '',            //Adding the URI provided here with the root url to our backend gets you the download link.
    verified: ''        //Enum value which comes out as a string when in JSON form.
                        //  verified can only be: 'UNVERIFIED', 'VERIFIED', or 'DENIED'
  },
  //studentevaluationform json. This will sometimes be null, but if not, appears in the format as follows:
  evaluationOfStudent:
}
//End of EmployeeProfileDescriptors JSON



//EmploymentContractDescriptors JSON (you will call these employmentcontract's)
{
  docid: 0,           //numeric doc id on the DB for this doc
  name: '',           //Self Explanatory (SE)
  description: '',    //SE
  instructions: '',   //SE
  uri: '',            //Adding the URI provided here with the root url to our backend gets you the download link.
  verified: ''        //Enum value which comes out as a string when in JSON form.
                      //  verified can only be: 'UNVERIFIED', 'VERIFIED', or 'DENIED'
}

//TaxFormsDescriptors JSON
{
  docid: 0,           //numeric doc id on the DB for this doc
  name: '',           //Name. Should be that of the actual tax form.
  year: '',           //Year in which this tax form applies.
  description: '',    //SE
  instructions: '',   //SE
  uri: '',            //Adding the URI provided here with the root url to our backend gets you the download link.
}
//End of TaxFormsDescriptors JSON



//StudentEvaluationFormDescriptors JSON
{
  docid: 0,           //numeric doc id on the DB for this doc
  name: '',           //Self Explanatory (SE)
  description: '',    //SE
  instructions: '',   //SE
  uri: '',            //Adding the URI provided here with the root url to our backend gets you the download link.
}
//End of StudentEvaluationFormDescriptors JSON



//InvitationDescriptors JSON
{
  id:0,                       //numeric id for this invitation in the DB
  employerId:0,               //numeric id for the employer in DB
  companyName:'',             //Self Explanatory (SE)
  employerFirstName:'',       //SE
  employerLastName:'',        //SE
  socialEventId:0,            //numeric id for the social event in the DB
  socialEventName:'',         //SE
  socialEventDescription:'',  //SE
  socialEventLocation:'',     //SE
  starts:'',                  //Starting date and time as a String
  ends:'',                    //Ending date and time as a String
  status:''                   //Attendance status of the employer to the event
                              //  status can only be 'UNKNOWN', 'ATTENDING' or 'REFUSED'.
}
//End of InvitationDescriptors JSON
