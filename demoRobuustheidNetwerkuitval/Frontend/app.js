const API = "http://localhost:8080/qr";

function log(msg) {
  document.getElementById("log").textContent += msg + "\n";
}

function getLocal() {
  return JSON.parse(localStorage.getItem("scans") || "[]");
}

function setLocal(data) {
  localStorage.setItem("scans", JSON.stringify(data));
}

async function generateQR() {
  const userId = document.getElementById("userId").value;
  const dynamic = document.getElementById("dynamic").checked;

  const res = await fetch(`${API}/generate?userId=${userId}&dynamic=${dynamic}`);
  const text = await res.text();

  document.getElementById("qrOutput").textContent = text;
}

function scan() {
  const fakeScan = "123:" + Date.now();
  const data = getLocal();
  data.push(fakeScan);
  setLocal(data);
  log("Opgeslagen offline: " + fakeScan);
}

async function sync() {
  let data = getLocal();
  let remaining = [];

  for (let item of data) {
    try {
      await fetch(`${API}/sync`, {
        method: "POST",
        body: item
      });
      log("Gesynct: " + item);
    } catch (e) {
      remaining.push(item);
      log("Mislukt, blijft offline: " + item);
    }
  }

  setLocal(remaining);
}