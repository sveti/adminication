const days = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
];

const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];
export function dynamicSort(property) {
  var sortOrder = 1;
  if (property[0] === "-") {
    sortOrder = -1;
    property = property.substr(1);
  }
  return function (a, b) {
    var result =
      a[property] < b[property] ? -1 : a[property] > b[property] ? 1 : 0;
    return result * sortOrder;
  };
}

export function upsert(array, item, keyName) {
  const i = array.findIndex((member) => member[keyName] === item[keyName]);
  if (i > -1) array[i] = item;
  else array.push(item);
}

export function updateObject(array, item, keyName, propertyToChange, newValue) {
  const i = array.findIndex((member) => member[keyName] === item[keyName]);
  if (i > -1) {
    array[i][propertyToChange] = newValue;
  }
}

export function getTodaysDate() {
  let curr = new Date();
  curr.setDate(curr.getDate());
  return curr.toISOString().substr(0, 10);
}

export function textToDayOfTheWeek(text) {
  let date = new Date(text);

  return days[date.getDay()];
}

export function textToDayOfTheWeekNumber(text) {
  let date = new Date(text);

  return date.getDay();
}

export function getMinDateAsDate(startDates) {
  const dates = startDates.map((dateStr) => {
    return new Date(dateStr);
  });
  let minDate = new Date(Math.min.apply(null, dates));
  return minDate;
}

export function textToDate(text) {
  return new Date(text);
}

export function isAfterDate(date1, date2) {
  return date1 >= date2;
}

export function getMinDate(startDates) {
  const dates = startDates.map((dateStr) => {
    return new Date(dateStr);
  });
  let minDate = new Date(Math.min.apply(null, dates));
  let dd = String(minDate.getDate()).padStart(2, "0");
  let mm = String(minDate.getMonth() + 1).padStart(2, "0"); //January is 0!
  let yyyy = minDate.getFullYear();

  return dd + "." + mm + "." + yyyy;
}

export function daysTillStart(startDate) {
  let Difference_In_Time =
    getMinDateFromArray(startDate).getTime() - Date.now();

  return Math.round(Difference_In_Time / (1000 * 3600 * 24));
}

export function getMinDateFromArray(startDate) {
  const dates = startDate.map((dateStr) => {
    return new Date(dateStr);
  });
  var minDate = new Date(Math.min.apply(null, dates));
  return minDate;
}

export function getMonthByDate(month) {
  let index = parseInt(month) - 1;
  return months[index];
}
