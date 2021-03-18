export function dynamicSort(property) {
  var sortOrder = 1;
  if (property[0] === "-") {
    sortOrder = -1;
    property = property.substr(1);
  }
  return function (a, b) {
    /* next line works with strings and numbers,
     * and you may want to customize it to your needs
     */
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
