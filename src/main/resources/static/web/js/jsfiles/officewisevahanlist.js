$(document).ready(function () {
    const pathParts = window.location.pathname.split('/'); // Split the URL path
    const purCd = pathParts[pathParts.length - 1]; // Get the last part of the path as purCd

    // Log the values to verify
    console.log('purCd:', purCd); // Check if this logs the correct purCd

    // Update the header with the service name (modify this as needed)
    $('#list .card-header strong').text(`Office wise list for Service ${purCd}`);

    const date = new Date();

    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    // This arrangement can be altered based on how we want the date's format to appear.
    let currentDate = `${day}-${month}-${year}`;
    console.log(currentDate);

    // Make the API call with the purCd
    fetchOfficeWiseData(purCd, currentDate);
});

function fetchOfficeWiseData(purCd, currentDate) {
    $.ajax({
        url: '/secure/api/officeWiseCount',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "stateCd": "ML",
            "purCd": purCd,
            "fromDate": "06-06-2023",
            "uptoDate": currentDate
        }),
        success: function(response) {
             // Log the response for debugging
             console.log("API Response:", response);
             const reportList = response.api; // Use the 'api' key directly

             // Check if reportList is an array
             if (Array.isArray(reportList) && reportList.length > 0) {
                 // Clear the table
                 $('#officewisetable tbody').empty();
                 $('#list .card-header strong').text(`Office wise list for :  ${reportList[0].purDesc}`);

                 // Populate the table with the fetched data
                 reportList.forEach(function (office) {
                    const officeName = getOfficeNameById(office.offCd);
                     const row = `
                         <tr>
                             <td>${officeName}</td>  <!-- Use purDesc for the office description -->
                             <td>${office.applied}</td>
                             <td>${office.approved}</td>
                             <td>${office.rejected}</td>
                             <td>${office.pending}</td>
                         </tr>`;
                     $('#officewisetable tbody').append(row);
                 });
             } else {
                 $('#officewisetable tbody').append('<tr><td colspan="5">No data available.</td></tr>');
             }
         },
        error: function(xhr, status, error) {
            console.error('API call failed:', error);
            $('#officewisetable tbody').append('<tr><td colspan="5">Failed to load data.</td></tr>');
        }
    });

    const offices = [
      { officeId: 4, officeName: "JOWAI" },
      { officeId: 5, officeName: "SHILLONG" },
      { officeId: 6, officeName: "NONGSTOIN" },
      { officeId: 7, officeName: "WILLIAMNAGAR" },
      { officeId: 8, officeName: "TURA" },
      { officeId: 9, officeName: "BAGHMARA" },
      { officeId: 10, officeName: "NONGPOH" },
      { officeId: 11, officeName: "KHLIEHRIAT" },
      { officeId: 12, officeName: "MAWKYRWAT" },
      { officeId: 13, officeName: "RESUBELPARA" },
      { officeId: 14, officeName: "AMPATI" },
      { officeId: 15, officeName: "MAIRANG" },
      { officeId: 99, officeName: "STATE TRANSPORT AUTHORITY" }
    ];


    function getOfficeNameById(officeId) {
      const office = offices.find(o => o.officeId === officeId);
      return office ? office.officeName : "Office not found";
    }
}