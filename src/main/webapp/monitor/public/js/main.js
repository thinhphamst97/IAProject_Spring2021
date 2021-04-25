// SOCKET IO
const socket = io();
// SELECT ELEMENTS
const labelRam = document.querySelector('.ram-label');
const progRam = document.querySelector('.ram-bar');
const labelCpu = document.querySelector('.cpu-label');
const progCpu = document.querySelector('.cpu-bar');
const usageSpaceLable = document.querySelector('.usagespace-lable');
const usageSpaceValue = document.querySelector('.usagespace-bar');
const valueTransmitEns33 = document.querySelector('.transmit-value-ens33');
const valueReceiveEns33 = document.querySelector('.receive-value-ens33');
const valueTransmitEns34 = document.querySelector('.transmit-value-ens34');
const valueReceiveEns34 = document.querySelector('.receive-value-ens34');
const valueClientTotal = document.querySelector('.client-total-value');
const valueClientOnline = document.querySelector('.client-online-value');
const valueClientWindows = document.querySelector('.client-windows-value');
const valueClientLinux = document.querySelector('.client-linux-value');
const valueImageTotal = document.querySelector('.image-total-value');
// const valueUpload = document.querySelector('.upload-value');
// const user = document.querySelector('.user');
// const os = document.querySelector('.os');

// ON CONNECT EVENT
socket.on('connect', () => {
    console.log('Connected');
});

// ON RAM USAGE EVENT
socket.on('ram-usage', ({ ram, cpu, diskUsage, diskTotal, 
    networkTransmitEns33, networkReceiveEns33, networkTransmitEns34, networkReceiveEns34,
    clientTotal, imageTotal, clientOnline, clientWindows, clientLinux}) => {

    // SHOW OS USER INFO
    // user.innerHTML = `<span>User: ${username}</span>`;
    // os.innerHTML = `<span>OS: ${osInfo === 'Windows_NT' ? 'Microsoft Windows' : osInfo}</span>`
    // Set ram label
    labelRam.innerHTML = `<span>RAM: ${ram} % </span>`;
    // Set Ram bar
    progRam.value = ram;
    // Set cpu label
    labelCpu.innerHTML = `<span>CPU: ${cpu} %</span>`;
    // Set cpu bar
    progCpu.value = cpu;
    // Set disk lable
    usageSpaceLable.innerHTML = `<span>${diskUsage} GB / ${diskTotal} GB </span>`;

    // Set disk value
    usageSpaceValue.max = diskTotal;
    usageSpaceValue.value = diskUsage;

    //Ens33
    networkTransmitEns33 = parseFloat(networkTransmitEns33).toFixed(2);
    networkReceiveEns33 = parseFloat(networkReceiveEns33).toFixed(2);
    valueTransmitEns33.innerHTML = `<span>Transmit: ${networkTransmitEns33} Kbit/s</span>`;
    valueReceiveEns33.innerHTML = `<span>Receive: ${networkReceiveEns33} Kbit/s</span>`;
    //Ens34
    networkTransmitEns34 = parseFloat(networkTransmitEns34).toFixed(2);
    networkReceiveEns34 = parseFloat(networkReceiveEns34).toFixed(2);
    valueTransmitEns34.innerHTML = `<span>Transmit: ${networkTransmitEns34} Kbit/s</span>`;
    valueReceiveEns34.innerHTML = `<span>Receive: ${networkReceiveEns34} Kbit/s</span>`;

    //Server information clientTotal, imageTotal, clientOnline, clientWindows, clientLinux
    valueClientTotal.innerHTML = `<span> ${clientTotal} </span>`;
    valueClientOnline.innerHTML = `<span> ${clientOnline} </span>`;
    valueClientWindows.innerHTML = `<span> ${clientWindows} </span>`;
    valueClientLinux.innerHTML = `<span> ${clientLinux} </span>`;
    valueImageTotal.innerHTML = `<span> ${imageTotal} </span>`;
    // valueDownload.innerHTML = `<span>${uploadValue}</span>`
    // Check
    // if (cpu > 90) {
    //     notify(cpu)
    // }
});

// NOTIFICATION FUNCTION
// let notify = (info) => {
//     // If granted
//     if (Notification.permission === 'granted') {
//         new Notification('Title', {
//             body: `CPU over ${info} %`
//         });
//     }
//     // If denied
//     if (Notification.permission !== 'denied') {
//         Notification.requestPermission()
//             .then((permission) => {
//                 if (permission === 'granted') {
//                     new Notification('Title', {
//                         body: `CPU over ${info} %`
//                     });
//                 };
//             });
//     };

// };