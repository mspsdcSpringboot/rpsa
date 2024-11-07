$(document).ready(function () {

    // Function to fetch total count
    function fetchTotalCount() {
        $.ajax({
            url: '/api/totalCount',  // Call your proxy endpoint
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                "stateCd": "ML"
            }),
            success: function (response) {
                // Check if the response is valid and contains the data
                if (response.api && response.api.length > 0) {
                    const data = response.api[0];  // Extract the first object in the 'api' array

                    // Update the dashboard values with the API response data
                    $('#applications-applied').text(data.applied);
                    $('#applications-approved').text(data.approved);
                    $('#applications-pending').text(data.pending);
                    $('#applications-rejected').text(data.rejected);
                } else {
                    console.error("Invalid API response structure");
                }
            },
            error: function (xhr, status, error) {
                // Handle errors if the API request fails
                console.error('API call failed:', error);
            }
        });
    }

    // Function to fetch service-wise count
    function fetchServiceWiseCount() {
        $('#loading').show(); // Show loading message
        $('#userhompagetable tbody').empty(); // Clear the current table rows

        $.ajax({
            url: '/api/serviceWiseCount', // Call your proxy endpoint
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                "stateCd": "ML"
            }),
            success: function (response) {
                const data = response.api; // Directly use the api array from the response

                // Loop through the API data and append rows to the table
                data.forEach(function (service) {
                    const row = `
                        <tr>
                            <td>
                                <a href="/secure/officewiselisvahan/${encodeURIComponent(service.purCd)}">${service.purDesc}</a>
                            </td>
                            <td>${service.applied}</td>
                            <td>${service.approved}</td>
                            <td>${service.rejected}</td>
                            <td>${service.pending}</td>
                        </tr>`;
                    $('#userhompagetable tbody').append(row);
                });
            },
            error: function (xhr, status, error) {
                console.error('API call failed:', error);
                $('#userhompagetable tbody').append('<tr><td colspan="5">Failed to load data.</td></tr>');
            },
            complete: function() {
                $('#loading').hide(); // Hide loading message
            }
        });
    }

    // Fetch both counts on page load
    fetchTotalCount();
    fetchServiceWiseCount();
});