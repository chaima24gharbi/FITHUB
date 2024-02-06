<?php

namespace App\Controller;

use App\Entity\Consultation;
use App\Entity\Fiche;
use App\Repository\ConsultationRepository;
use App\Repository\FicheRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Exception;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/mobile/fiche")
 */
class FicheMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(FicheRepository $ficheRepository): Response
    {
        $fiches = $ficheRepository->findAll();

        if ($fiches) {
            return new JsonResponse($fiches, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, ConsultationRepository $consultationRepo): JsonResponse
    {
        $fiche = new Fiche();

        return $this->manage($fiche, $request, false, $consultationRepo);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, FicheRepository $ficheRepository, ConsultationRepository $consultationRepo): Response
    {
        $fiche = $ficheRepository->find((int)$request->get("id"));

        if (!$fiche) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($fiche, $request, true, $consultationRepo);
    }

    public function manage($fiche, $request, $isEdit, $consultationRepo): JsonResponse
    {

        try {
            $consultation = $consultationRepo->find((int)$request->get("consultation"));
            if (!$consultation) {
                return new JsonResponse("consultation with id " . (int)$request->get("consultation") . " does not exist", 203);
            }


            $fiche->constructor(
                $request->get("descriptionFiche"),
                $request->get("nom"),
                $request->get("category")
            );



            $fiche->removeConsultations();

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($fiche);

            $fiche->setConsultation($consultation);

            $entityManager->persist($fiche);

            $entityManager->flush();

            return new JsonResponse($fiche, 200);
        } catch (Exception) {
            return new JsonResponse([], 250);
        }
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, FicheRepository $ficheRepository): JsonResponse
    {
        $fiche = $ficheRepository->find((int)$request->get("id"));

        if (!$fiche) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($fiche);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, FicheRepository $ficheRepository): Response
    {
        $fiches = $ficheRepository->findAll();

        foreach ($fiches as $fiche) {
            $entityManager->remove($fiche);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
}
