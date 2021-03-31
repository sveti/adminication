const days = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
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
