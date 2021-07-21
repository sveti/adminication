import React from "react";
import {
  useTable,
  useSortBy,
  useFilters,
  useGlobalFilter,
  useAsyncDebounce,
} from "react-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSortDown, faSortUp } from "@fortawesome/free-solid-svg-icons";
import { matchSorter } from "match-sorter";
import StatusBadge from "../../common/StausBadge";
import LevelBadge from "../../common/LevelBadge";
import { Link } from "react-router-dom";

// Define a default UI for filtering
function GlobalFilter({
  preGlobalFilteredRows,
  globalFilter,
  setGlobalFilter,
}) {
  const count = preGlobalFilteredRows.length;
  const [value, setValue] = React.useState(globalFilter);
  const onChange = useAsyncDebounce((value) => {
    setGlobalFilter(value || undefined);
  }, 200);

  return (
    <div className="row mb-3">
      <div className="col-2 text-right mt-1 biggerLabel">
        <label className="">Search: </label>
      </div>
      <div className="col-10">
        <input
          className="globalSearchInput form-control"
          value={value || ""}
          onChange={(e) => {
            setValue(e.target.value);
            onChange(e.target.value);
          }}
          placeholder={`${count} records...`}
          style={{
            fontSize: "1.1rem",
          }}
          autoFocus
        />
      </div>
    </div>
  );
}

// Define a default UI for filtering
function DefaultColumnFilter({
  column: { filterValue, preFilteredRows, setFilter },
}) {
  const count = preFilteredRows.length;

  return (
    <input
      className="form-control my-2"
      value={filterValue || ""}
      onChange={(e) => {
        setFilter(e.target.value || undefined); // Set undefined to remove the filter entirely
      }}
      placeholder={`Search ${count} records...`}
    />
  );
}

// This is a custom filter UI for selecting
// a unique option from a list
function SelectColumnFilter({
  column: { filterValue, setFilter, preFilteredRows, id },
}) {
  // Calculate the options for filtering
  // using the preFilteredRows
  const options = React.useMemo(() => {
    const options = new Set();
    preFilteredRows.forEach((row) => {
      options.add(row.values[id]);
    });
    return [...options.values()];
  }, [id, preFilteredRows]);

  // Render a multi-select box
  return (
    <select
      className="custom-select my-2"
      value={filterValue}
      onChange={(e) => {
        setFilter(e.target.value || undefined);
      }}
    >
      <option value="">All</option>
      {options.map((option, i) => (
        <option key={i} value={option}>
          {option}
        </option>
      ))}
    </select>
  );
}

// This is a custom UI for our 'between' or number range
// filter. It uses two number boxes and filters rows to
// ones that have values between the two
function NumberRangeColumnFilter({
  column: { filterValue = [], preFilteredRows, setFilter, id },
}) {
  const [min, max] = React.useMemo(() => {
    let min = preFilteredRows.length ? preFilteredRows[0].values[id] : 0;
    let max = preFilteredRows.length ? preFilteredRows[0].values[id] : 0;
    preFilteredRows.forEach((row) => {
      min = Math.min(row.values[id], min);
      max = Math.max(row.values[id], max);
    });
    return [min, max];
  }, [id, preFilteredRows]);

  return (
    <div className="row px-3">
      <input
        className=" form-control my-2 col-5"
        value={filterValue[0] || ""}
        type="number"
        onChange={(e) => {
          const val = e.target.value;
          setFilter((old = []) => [
            val ? parseInt(val, 10) : undefined,
            old[1],
          ]);
        }}
        placeholder={min}
        style={{
          width: "90px",
        }}
      />
      <span className="my-2 col-2">to</span>
      <input
        className=" form-control my-2 col-5"
        value={filterValue[1] || ""}
        type="number"
        onChange={(e) => {
          const val = e.target.value;
          setFilter((old = []) => [
            old[0],
            val ? parseInt(val, 10) : undefined,
          ]);
        }}
        placeholder={max}
        style={{
          width: "90px",
        }}
      />
    </div>
  );
}

function fuzzyTextFilterFn(rows, id, filterValue) {
  return matchSorter(rows, filterValue, { keys: [(row) => row.values[id]] });
}

// Let the table remove the filter if the string is empty
fuzzyTextFilterFn.autoRemove = (val) => !val;

function Table({ columns, data }) {
  const filterTypes = React.useMemo(
    () => ({
      // Add a new fuzzyTextFilterFn filter type.
      fuzzyText: fuzzyTextFilterFn,
      // Or, override the default text filter to use
      // "startWith"
      text: (rows, id, filterValue) => {
        return rows.filter((row) => {
          const rowValue = row.values[id];
          return rowValue !== undefined
            ? String(rowValue)
                .toLowerCase()
                .startsWith(String(filterValue).toLowerCase())
            : true;
        });
      },
    }),
    []
  );
  const defaultColumn = React.useMemo(
    () => ({
      // Let's set up our default Filter UI
      Filter: DefaultColumnFilter,
    }),
    []
  );

  // Use the state and functions returned from useTable to build your UI
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    state,
    visibleColumns,
    preGlobalFilteredRows,
    setGlobalFilter,
  } = useTable(
    {
      columns,
      data,
      defaultColumn, // Be sure to pass the defaultColumn option
      filterTypes,
    },
    useFilters, // useFilters!
    useGlobalFilter, // useGlobalFilter!
    useSortBy
  );
  // Render the UI for your table
  return (
    <table className="mx-auto allCoursesTable" {...getTableProps()}>
      <thead className="allCoursesTableHead">
        <tr>
          <th>
            <div className="mb-3">
              <Link
                className="nav-link"
                to={{
                  pathname: "/courses/add",
                }}
              >
                <button className="editButton my-0 btn-lg">Add Course</button>
              </Link>
            </div>
          </th>
          <th
            colSpan={visibleColumns.length}
            style={{
              textAlign: "left",
            }}
          >
            <GlobalFilter
              preGlobalFilteredRows={preGlobalFilteredRows}
              globalFilter={state.globalFilter}
              setGlobalFilter={setGlobalFilter}
            />
          </th>
        </tr>
        {headerGroups.map((headerGroup) => (
          <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map((column) => (
              <th key={column.id} className="allCoursesTh">
                <div {...column.getHeaderProps(column.getSortByToggleProps())}>
                  {column.render("Header")}
                  <span>
                    {column.isSorted ? (
                      column.isSortedDesc ? (
                        <FontAwesomeIcon icon={faSortUp}></FontAwesomeIcon>
                      ) : (
                        <FontAwesomeIcon icon={faSortDown}></FontAwesomeIcon>
                      )
                    ) : (
                      ""
                    )}
                  </span>
                </div>

                <div>{column.canFilter ? column.render("Filter") : null}</div>
              </th>
            ))}
          </tr>
        ))}
      </thead>
      <tbody className="allCoursesTableBody" {...getTableBodyProps()}>
        {rows.map((row, i) => {
          prepareRow(row);
          return (
            <tr className="allCoursesRow" {...row.getRowProps()}>
              {row.cells.map((cell) => {
                return (
                  <td className="allCoursesTd" {...cell.getCellProps()}>
                    {cell.render("Cell")}
                  </td>
                );
              })}
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}

const AdminCoursesTable = (props) => {
  const columns = React.useMemo(
    () => [
      {
        Header: "Course",
        columns: [
          {
            Header: "ID",
            ///rounabout to pass id later to link
            accessor: (row) => row.id,
          },
          {
            Header: "Title",
            accessor: "title",
          },
        ],
      },
      {
        Header: "Info",
        columns: [
          {
            Header: "Status",
            accessor: "status",
            Filter: SelectColumnFilter,
            filter: "includes",
            Cell: ({ cell: { value } }) => <StatusBadge status={value} />,
          },
          {
            Header: "Level",
            accessor: "level",
            Filter: SelectColumnFilter,
            filter: "includes",
            Cell: ({ cell: { value } }) => <LevelBadge level={value} />,
          },
          {
            Header: "Price/Student",
            accessor: "pricePerStudent",
            Filter: NumberRangeColumnFilter,
            filter: "between",
          },
        ],
      },
      {
        Header: "Details",
        accessor: "id",
        filterable: false,
        Cell: (e) => (
          <Link
            className="nav-link"
            to={{
              pathname: "/courses/" + e.value,
              state: {
                courseId: e.value,
              },
            }}
          >
            <button className="editButton my-0">Details</button>
          </Link>
        ),
      },
    ],
    []
  );
  const data = React.useMemo(() => props.data, [props.data]);

  return (
    <div>
      <Table columns={columns} data={data} />
    </div>
  );
};

export default AdminCoursesTable;
