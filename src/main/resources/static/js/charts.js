<!--Charts-->
    async function fetchData() {
    const response = await fetch('/api/elementary-schools');
    const data = await response.json();
    return data;
}

    function createBarChart(data) {
    const ctx = document.getElementById('myBarChart').getContext('2d');
    const labels = data.map(school => school.schoolName); // Adjust according to your model
    const values = data.map(school => school.year); // Adjust according to your model

    new Chart(ctx, {
    type: 'bar',
    data: {
    labels: labels,
    datasets: [{
    label: 'Elementary Schools Data',
    data: values,
    backgroundColor: '#0E47AB',
    borderColor: '#0E47AB',
    borderWidth: 1
}]
},
    options: {
    responsive: true,
    scales: {
    y: {
    beginAtZero: true
}
}
}
});
}

    function createPieChart(data) {
    const ctx = document.getElementById('myPieChart').getContext('2d');
    const labels = data.map(school => school.schoolName); // Adjust according to your model
    const values = data.map(school => school.year); // Adjust according to your model

    new Chart(ctx, {
    type: 'pie',
    data: {
    labels: labels,
    datasets: [{
    label: 'Elementary Schools Data',
    data: values,
    backgroundColor: [
    '#0E47AB',
    '#FF6384',
    '#36A2EB',
    '#FFCE56',
    '#4BC0C0',
    '#9966FF'
    ],
    borderColor: [
    '#0E47AB',
    '#FF6384',
    '#36A2EB',
    '#FFCE56',
    '#4BC0C0',
    '#9966FF'
    ],
    borderWidth: 1
}]
},
    options: {
    responsive: true,
    plugins: {
    legend: {
    position: 'top',
},
    tooltip: {
    enabled: true
}
}
}
});
}

    fetchData().then(data => {
    createBarChart(data);
    createPieChart(data);
});

