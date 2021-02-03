import React from "react";
import { Accordion, AccordionItem } from "react-light-accordion";
import "react-light-accordion/demo/css/index.css";
import AttendanceTable from "./AttendanceTable";
import "./lessons.css";

function LessonsList(props) {
  const lessons = props.lessons;

  return (
    <div className="lessonsAccordion">
      <Accordion atomic={true}>
        {lessons.map((lesson, index) => {
          return (
            <AccordionItem key={lesson.id} title={lesson.date}>
              <div className="lessonContent">
                {lesson.description}
                <AttendanceTable
                  courseId={lesson.courseId}
                  students={props.students}
                ></AttendanceTable>
              </div>
            </AccordionItem>
          );
        })}
      </Accordion>
    </div>
  );
}

export default LessonsList;
