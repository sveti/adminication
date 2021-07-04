import React from "react";
import { Accordion, AccordionItem } from "react-light-accordion";
import "react-light-accordion/demo/css/index.css";
import AddAttendesTable from "./AddAttendancesTable";
import AttendanceTable from "./AttendanceTable";
import "./lessons.css";
import Description from "./Description";

export default function LessonsList(props) {
  const { lessons, attendances, students } = props;
  return lessons.length > 0 ? (
    <div className="lessonsAccordion">
      <Accordion atomic={true}>
        {lessons.map((lesson) => {
          ///are there attendances for this lesson
          return attendances.filter((a) => a.lessonId === lesson.id).length >
            0 ? (
            ///edit existing attendances
            <AccordionItem key={lesson.id} id={lesson.id} title={lesson.date}>
              <div className="lessonContent">
                <Description
                  lessonId={lesson.id}
                  description={lesson.description}
                ></Description>

                <AttendanceTable
                  courseId={lesson.courseId}
                  students={students}
                  attendances={attendances.filter(
                    (a) => a.lessonId === lesson.id
                  )}
                ></AttendanceTable>
              </div>
            </AccordionItem>
          ) : (
            //  there aren't any attendances => add attendances component
            <AccordionItem key={lesson.id} title={lesson.date}>
              <div className="lessonContent">
                <Description
                  lessonId={lesson.id}
                  description={lesson.description}
                ></Description>
                <AddAttendesTable
                  courseId={lesson.courseId}
                  students={students}
                  lessonId={lesson.id}
                  attendances={null}
                ></AddAttendesTable>
              </div>
            </AccordionItem>
          );
        })}
      </Accordion>
    </div>
  ) : (
    <h3>
      There are no lessons to this course! Use the form to add your first
      lesson!
    </h3>
  );
}
