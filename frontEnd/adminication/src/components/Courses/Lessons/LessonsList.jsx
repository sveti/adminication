import React, { Component } from "react";
import { Accordion, AccordionItem } from "react-light-accordion";
import "react-light-accordion/demo/css/index.css";
import AddAttendesTable from "./AddAttendancesTable";
import AttendanceTable from "./AttendanceTable";
import "./lessons.css";

class LessonsList extends Component {
  state = {
    lessons: this.props.lessons,
    attendances: this.props.attendances,
    students: this.props.students,
    addAttendance: false,
  };

  handleAddAttendance = (lessonid) => {
    console.log(lessonid);
    this.setState({ addAttendance: true });
  };

  render() {
    const { lessons, attendances, students, addAttendance } = this.state;

    return (
      <div className="lessonsAccordion">
        <Accordion atomic={true}>
          {lessons.map((lesson) => {
            return attendances.filter((a) => a.lessonId === lesson.id)
              .length ? (
              <AccordionItem key={lesson.id} title={lesson.date}>
                <div className="lessonContent">
                  {lesson.description}
                  <AttendanceTable
                    courseId={lesson.courseId}
                    students={students}
                    attendances={
                      attendances
                        ? attendances.filter((a) => a.lessonId === lesson.id)
                        : null
                    }
                  ></AttendanceTable>
                </div>
              </AccordionItem>
            ) : (
              <AccordionItem key={lesson.id} title={lesson.date}>
                <div className="lessonContent">
                  {lesson.description}
                  <div>
                    <h4>No attendances for this lecture</h4>
                    <button
                      className="welcomeButton"
                      onClick={() => this.handleAddAttendance(lesson.id)}
                    >
                      Add attendances
                    </button>
                  </div>
                  {addAttendance ? (
                    <AddAttendesTable
                      courseId={lesson.courseId}
                      students={students}
                      attendances={
                        attendances
                          ? attendances.filter((a) => a.lessonId === lesson.id)
                          : null
                      }
                    ></AddAttendesTable>
                  ) : null}
                </div>
              </AccordionItem>
            );
          })}
        </Accordion>
      </div>
    );
  }
}

export default LessonsList;
